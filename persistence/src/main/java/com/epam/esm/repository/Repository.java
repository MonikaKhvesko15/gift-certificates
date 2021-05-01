package com.epam.esm.repository;

import com.epam.esm.specification.CriteriaSpecification;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T save(T model);

    T update(T model);

    void deleteById(Long id);

    Optional<T> getById(Long id);

    Optional<T> getByName(String name);

    List<T> getEntityListBySpecification(CriteriaSpecification<T> specification);

    Optional<T> getEntityBySpecification(CriteriaSpecification<T> specification);
}
