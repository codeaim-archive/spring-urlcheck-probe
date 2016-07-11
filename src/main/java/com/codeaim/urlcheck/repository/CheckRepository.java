package com.codeaim.urlcheck.repository;

import java.time.Instant;
import java.util.Collection;

import com.codeaim.urlcheck.domain.CheckDto;

public interface CheckRepository extends CrudRepository<CheckDto, Long>
{
    Collection<CheckDto> findElectableChecks(String probe, boolean isClustered, Instant instant, long candidatePoolSize);

    Collection<CheckDto> markChecksElected(Collection<CheckDto> checkDtos);

    int batchUpdate(Collection<CheckDto> checkDtos);
}
