package com.codeaim.urlcheck.repository;

import java.util.Collection;
import java.util.Optional;

public interface CrudRepository<T, ID>
{
    long count();

    void delete(T entity);

    void deleteAll();

    boolean exists(ID id);

    Collection<T> findAll();

    Collection<T> findAll(Collection<ID> ids);

    Optional<T> findOne(ID id);

    T save(T entity);

    Collection<T> save(Collection<T> entities);
}
