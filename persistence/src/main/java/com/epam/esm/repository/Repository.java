package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.specification.SqlSpecification;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity> {
    T save(T t);

    boolean deleteById(Long id);

    List<T> query(SqlSpecification specification);

    T getById(Long id);

    Optional<T> getByName(String name);
}
