package com.codeaim.urlcheck.task;

import com.codeaim.urlcheck.configuration.ProbeConfiguration;
import com.codeaim.urlcheck.domain.CheckDto;
import com.codeaim.urlcheck.domain.ResultDto;
import com.codeaim.urlcheck.domain.State;
import com.codeaim.urlcheck.domain.Status;
import com.codeaim.urlcheck.repository.CheckRepository;
import com.codeaim.urlcheck.repository.ResultRepository;
import com.codeaim.urlcheck.utility.Futures;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ScheduledCheckTask implements CheckTask
{
    private OkHttpClient httpClient;
    private ExecutorService executorService;
    private CheckRepository checkRepository;
    private ResultRepository resultRepository;
    private ProbeConfiguration probeConfiguration;

    @Autowired
    public ScheduledCheckTask(
            OkHttpClient httpClient,
            ExecutorService executorService,
            CheckRepository checkRepository,
            ResultRepository resultRepository,
            ProbeConfiguration probeConfiguration
    )
    {
        this.httpClient = httpClient;
        this.executorService = executorService;
        this.checkRepository = checkRepository;
        this.resultRepository = resultRepository;
        this.probeConfiguration = probeConfiguration;
    }

    public void run()
    {
        Collection<CheckDto> electableChecks = findElectableChecks(
                checkRepository,
                probeConfiguration.getName(),
                probeConfiguration.isClustered(),
                probeConfiguration.getCandidatePoolSize());

        if (!electableChecks.isEmpty())
        {
            List<CheckDto> electedChecks = markChecksElected(
                    checkRepository,
                    electableChecks);

            if (!electedChecks.isEmpty())
            {
                List<Pair<CheckDto, Optional<Response>>> checkResponses = requestCheckResponses(
                        httpClient,
                        electedChecks);

                if (!checkResponses.isEmpty())
                {
                    Collection<Pair<CheckDto, ResultDto>> checkResults = createCheckResults(
                            resultRepository,
                            probeConfiguration.getName(),
                            checkResponses);

                    if (!checkResults.isEmpty())
                    {
                        Collection<CheckDto> updatedElectedChecks = updateCheckStatus(
                                checkRepository,
                                probeConfiguration.getName(),
                                checkResults);

                        if (!updatedElectedChecks.isEmpty())
                            updateChecks(
                                    checkRepository,
                                    updatedElectedChecks);
                    }
                }
            }
        }
    }

    private List<Pair<CheckDto, Optional<Response>>> requestCheckResponses(
            OkHttpClient httpClient,
            List<CheckDto> electedChecks
    )
    {
        try
        {
            List<Optional<Response>> responses = Futures.complete(electedChecks
                    .stream()
                    .map(CheckDto::getUrl)
                    .map(checkUrl -> new Request.Builder()
                            .url(checkUrl)
                            .build())
                    .map(checkUrlRequest ->
                            CompletableFuture.supplyAsync(() -> requestCheckResponse(httpClient, checkUrlRequest), executorService))
                    .collect(Collectors.toList()))
                    .get();

            return IntStream
                    .range(0, electedChecks.size())
                    .mapToObj(index -> Pair.of(electedChecks.get(index), responses.get(index)))
                    .collect(Collectors.toList());
        } catch (Exception e)
        {
            return Collections.emptyList();
        }
    }

    private Optional<Response> requestCheckResponse(
            OkHttpClient httpClient,
            Request checkUrlRequest
    )
    {
        System.out.println("Making request for " + checkUrlRequest.url().toString());
        try
        {
            return Optional.of(httpClient.newCall(checkUrlRequest).execute());
        } catch (IOException e)
        {
            return Optional.empty();
        }
    }

    private Collection<CheckDto> findElectableChecks(
            CheckRepository checkRepository,
            String probe,
            boolean isClustered,
            long candidatePoolSize
    )
    {
        return checkRepository.findElectableChecks(
                probe,
                isClustered,
                Instant.now(),
                candidatePoolSize);
    }

    private List<CheckDto> markChecksElected(
            CheckRepository checkRepository,
            Collection<CheckDto> electableChecks
    )
    {
        return checkRepository
                .markChecksElected(electableChecks)
                .stream()
                .collect(Collectors.toList());
    }

    private Collection<Pair<CheckDto, ResultDto>> createCheckResults(
            ResultRepository resultRepository,
            String probe,
            List<Pair<CheckDto, Optional<Response>>> checkResponses
    )
    {
        List<ResultDto> checkResults = checkResponses
                .stream()
                .map(checkResponsePair -> ResultDto
                        .builder()
                        .checkId(checkResponsePair
                                .getKey()
                                .getId())
                        .previousResultId(checkResponsePair
                                .getKey()
                                .getLatestResultId())
                        .probe(probe)
                        .responseTime(checkResponsePair
                                .getValue()
                                .map(response -> OptionalLong.of(response.receivedResponseAtMillis() - response.sentRequestAtMillis()))
                                .orElse(OptionalLong.empty()))
                        .statusCode(checkResponsePair
                                .getValue()
                                .map(this::getHttpStatus)
                                .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                        .status(checkResponsePair
                                .getValue()
                                .map(this::getHttpStatus)
                                .orElse(HttpStatus.INTERNAL_SERVER_ERROR)
                                .is2xxSuccessful() ? Status.UP : Status.DOWN)
                        .changed(!Objects.equals(
                                checkResponsePair
                                        .getValue()
                                        .map(this::getHttpStatus)
                                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .is2xxSuccessful() ? Status.UP : Status.DOWN,
                                checkResponsePair.getKey().getStatus()))
                        .confirmation(checkResponsePair
                                .getKey()
                                .isConfirming())
                        .build())
                .collect(Collectors.toList());

        List<ResultDto> savedCheckResults = resultRepository.save(checkResults)
                .stream()
                .collect(Collectors.toList());

        return IntStream
                .range(0, checkResponses.size())
                .mapToObj(index -> {
                    checkResponses.get(index).getValue().ifPresent(Response::close);
                    return Pair.of(checkResponses.get(index).getKey(), savedCheckResults.get(index));
                })
                .collect(Collectors.toList());
    }

    private HttpStatus getHttpStatus(Response response)
    {
        try
        {
            return HttpStatus.valueOf(response.code());
        } catch (IllegalArgumentException ex)
        {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Collection<CheckDto> updateCheckStatus(
            CheckRepository checkRepository,
            String probe,
            Collection<Pair<CheckDto, ResultDto>> checkResults)
    {
        return checkResults
                .stream()
                .map(checkResultPair -> {
                    if (checkResultPair.getValue().isChanged() && checkResultPair.getValue().isConfirmation())
                        return checkRepository.save(
                                statusChangeConfirmed(
                                        checkResultPair.getKey(),
                                        checkResultPair.getValue(),
                                        probe));
                    if (!checkResultPair.getValue().isChanged() && checkResultPair.getValue().isConfirmation())
                        return checkRepository.save(
                                statusChangeConfirmationInconclusive(
                                        checkResultPair.getKey(),
                                        checkResultPair.getValue(),
                                        probe));
                    if (checkResultPair.getValue().isChanged())
                        return checkRepository.save(
                                statusChangeConfirmationRequired(
                                        checkResultPair.getKey(),
                                        checkResultPair.getValue(),
                                        probe));

                    return checkRepository.save(
                            statusChangeNone(
                                    checkResultPair.getKey(),
                                    checkResultPair.getValue(),
                                    probe));
                })
                .collect(Collectors.toList());
    }

    private CheckDto statusChangeNone(
            CheckDto checkDto,
            ResultDto resultDto,
            String probe
    )
    {
        return CheckDto
                .buildFrom(checkDto)
                .latestResultId(OptionalLong.of(resultDto.getId()))
                .refresh(Instant.now().plus(
                        checkDto.getInterval(),
                        ChronoUnit.MINUTES))
                .state(State.WAITING)
                .locked(Optional.empty())
                .probe(Optional.of(probe))
                .build();
    }

    private CheckDto statusChangeConfirmationRequired(
            CheckDto checkDto,
            ResultDto resultDto,
            String probe
    )
    {
        return CheckDto
                .buildFrom(checkDto)
                .latestResultId(OptionalLong.of(resultDto.getId()))
                .confirming(true)
                .state(State.WAITING)
                .locked(Optional.empty())
                .probe(Optional.of(probe))
                .build();
    }

    private CheckDto statusChangeConfirmationInconclusive(
            CheckDto checkDto,
            ResultDto resultDto,
            String probe
    )
    {
        return CheckDto
                .buildFrom(checkDto)
                .latestResultId(OptionalLong.of(resultDto.getId()))
                .confirming(false)
                .state(State.WAITING)
                .locked(Optional.empty())
                .probe(Optional.of(probe))
                .build();
    }

    private CheckDto statusChangeConfirmed(
            CheckDto checkDto,
            ResultDto resultDto,
            String probe
    )
    {
        return CheckDto
                .buildFrom(checkDto)
                .latestResultId(OptionalLong.of(resultDto.getId()))
                .status(resultDto.getStatus())
                .confirming(false)
                .refresh(Instant.now().plus(
                        checkDto.getInterval(),
                        ChronoUnit.MINUTES))
                .state(State.WAITING)
                .locked(Optional.empty())
                .probe(Optional.of(probe))
                .build();
    }

    private int updateChecks(
            CheckRepository checkRepository,
            Collection<CheckDto> updatedChecks
    )
    {
        return checkRepository
                .batchUpdate(updatedChecks);
    }
}












