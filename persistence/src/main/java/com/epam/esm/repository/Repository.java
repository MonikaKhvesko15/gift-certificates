package com.epam.esm.repository;

import com.epam.esm.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    //create
    void add(T t);

    //update
    void update(T t);

    //delete
    boolean deleteById(Integer id);

    //read for List result
    List<T> queryListResult(Specification<T> specification);

    //read for single result
    Optional<T> querySingleResult(Specification<T> specification);

}
