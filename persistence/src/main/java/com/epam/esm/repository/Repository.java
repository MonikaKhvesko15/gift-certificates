package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    //create
    T add(T t);

    //update
    T update(T t);

    //delete
    boolean deleteById(Integer id);

    //read for List result
    List<T> queryForListResult(Specification<T> specification);

    //read for single result
    Optional<T> queryForSingleResult(Specification<T> specification);

}
