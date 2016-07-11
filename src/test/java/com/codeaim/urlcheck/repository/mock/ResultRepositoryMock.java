package com.codeaim.urlcheck.repository.mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.codeaim.urlcheck.domain.ResultDto;
import com.codeaim.urlcheck.repository.ResultRepository;

public class ResultRepositoryMock implements ResultRepository
{

    @Override
    public int batchInsert(List<ResultDto> resultDtos)
    {
        return resultDtos.size();
    }

    @Override
    public long count()
    {
        return 2;
    }

    @Override
    public void delete(ResultDto entity)
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
    public Collection<ResultDto> findAll()
    {
        return Arrays.asList(
                ResultDto.builder()
                        .probe("probe")
                        .statusCode(HttpStatus.OK)
                        .build(),
                ResultDto.builder()
                        .probe("probe")
                        .statusCode(HttpStatus.OK)
                        .build());
    }

    @Override
    public Collection<ResultDto> findAll(Collection<Long> ids)
    {
        return findAll();
    }

    @Override
    public Optional<ResultDto> findOne(Long id)
    {
        return findAll().stream().findFirst();
    }

    @Override
    public ResultDto save(ResultDto entity)
    {
        return entity;
    }

    @Override
    public Collection<ResultDto> save(Collection<ResultDto> entities)
    {
        return entities;
    }
}
