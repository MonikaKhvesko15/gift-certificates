package com.epam.esm.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface CriteriaSpecification<T> {
    CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder builder);
}
