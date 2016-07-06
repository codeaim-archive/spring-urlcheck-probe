package com.codeaim.urlcheck.repository;

import com.codeaim.urlcheck.domain.CheckDto;

import java.time.Instant;
import java.util.Collection;

public interface CheckRepository extends CrudRepository<CheckDto, Long>
{
    Collection<CheckDto> findElectableChecks(String probe, boolean isClustered, Instant instant, long candidatePoolSize);

    Collection<CheckDto> markChecksElected(Collection<CheckDto> checkDtos);

    int batchUpdate(Collection<CheckDto> checkDtos);
}
