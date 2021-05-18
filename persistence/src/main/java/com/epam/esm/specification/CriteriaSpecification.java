package com.epam.esm.specification;

import com.epam.esm.entity.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface CriteriaSpecification<T extends BaseEntity> {
    CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder builder);
}
