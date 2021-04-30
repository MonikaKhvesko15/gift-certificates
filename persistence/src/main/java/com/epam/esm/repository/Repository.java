package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T save(T model);

    T update(T model);

    void deleteById(Long id);

    List<T> findAll();

    Optional<T> getById(Long id);

    Optional<T> getByName(String name);
}
