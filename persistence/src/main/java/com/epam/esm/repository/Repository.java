package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.specification.SqlSpecification;
import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity> {
    //create
    Long save(T t);

    //delete
    boolean deleteById(Integer id);

    //read for List result
    List<T> queryForListResult(SqlSpecification<T> specification);

    //read for single result
    Optional<T> queryForSingleResult(SqlSpecification<T> specification);

}
