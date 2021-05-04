package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.CriteriaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Repository<T extends BaseEntity> {
    T save(T model);

    T update(T model);

    void delete(T entity);

    Optional<T> getById(Long id);

    Optional<T> getByName(String name);

    Page<T> getEntityListBySpecification(CriteriaSpecification<T> specification, Pageable pageable);

    Optional<T> getEntityBySpecification(CriteriaSpecification<T> specification);
}
