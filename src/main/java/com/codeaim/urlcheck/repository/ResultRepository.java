package com.codeaim.urlcheck.repository;

import com.codeaim.urlcheck.domain.ResultDto;

import java.util.List;

public interface ResultRepository extends CrudRepository<ResultDto, Long>
{
    int expireResults(int resultExpiryLimit);

    int batchInsert(List<ResultDto> resultDtos);
}
