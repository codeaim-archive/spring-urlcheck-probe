package com.codeaim.urlcheck.repository;

import com.codeaim.urlcheck.domain.ResultDto;

import java.util.Collection;
import java.util.List;

public interface ResultRepository extends CrudRepository<ResultDto, Long>
{
    int[] batchInsert(List<ResultDto> resultDtos);
}
