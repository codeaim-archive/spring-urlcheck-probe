package com.codeaim.urlcheck.repository;

import java.util.List;

import com.codeaim.urlcheck.domain.ResultDto;

public interface ResultRepository extends CrudRepository<ResultDto, Long>
{
    int batchInsert(List<ResultDto> resultDtos);
}
