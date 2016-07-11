package com.codeaim.urlcheck.repository.mock;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.codeaim.urlcheck.domain.CheckDto;
import com.codeaim.urlcheck.domain.Status;
import com.codeaim.urlcheck.repository.CheckRepository;

import okhttp3.HttpUrl;

public class CheckRepositoryMock implements CheckRepository
{
    @Override
    public Collection<CheckDto> findElectableChecks(String probe, boolean isClustered, Instant instant, long candidatePoolSize)
    {
        return findAll()
                .stream()
                .limit(candidatePoolSize)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<CheckDto> markChecksElected(Collection<CheckDto> checkDtos)
    {
        return checkDtos;
    }

    @Override
    public int batchUpdate(Collection<CheckDto> checkDtos)
    {
        return checkDtos.size();
    }

    @Override
    public long count()
    {
        return 2;
    }

    @Override
    public void delete(CheckDto entity)
    {
    }

    @Override
    public void deleteAll()
    {
    }

    @Override
    public boolean exists(Long id)
    {
        return true;
    }

    @Override
    public Collection<CheckDto> findAll()
    {
        return Arrays.asList(
                CheckDto.builder()
                        .name("name")
                        .url(HttpUrl.parse("http://www.example.com/"))
                        .confirming(true)
                        .status(Status.DOWN)
                        .build(),
                CheckDto.builder().name("name2")
                        .url(HttpUrl.parse("http://www.url-that-does-not-exist.com/"))
                        .confirming(true)
                        .status(Status.UP)
                        .build(),
                CheckDto.builder().name("name3")
                        .url(HttpUrl.parse("http://www.example.com/"))
                        .status(Status.DOWN)
                        .build(),
                CheckDto.builder().name("name4")
                        .url(HttpUrl.parse("http://www.example.com/"))
                        .status(Status.UP)
                        .build(),
                CheckDto.builder().name("name5")
                        .url(HttpUrl.parse("http://www.example.com/"))
                        .confirming(true)
                        .status(Status.UP)
                        .build());
    }

    @Override
    public Collection<CheckDto> findAll(Collection<Long> ids)
    {
        return findAll();
    }

    @Override
    public Optional<CheckDto> findOne(Long id)
    {
        return findAll().stream().findFirst();
    }

    @Override
    public CheckDto save(CheckDto entity)
    {
        return entity;
    }

    @Override
    public Collection<CheckDto> save(Collection<CheckDto> entities)
    {
        return entities;
    }
}
