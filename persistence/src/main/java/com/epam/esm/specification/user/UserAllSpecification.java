package com.epam.esm.specification.user;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.User;
import com.epam.esm.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserAllSpecification implements CriteriaSpecification<User> {
    @Override
    public CriteriaQuery<User> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot).distinct(true);
        Predicate isNotDeletedPredicate = builder.isFalse(userRoot.get(BaseEntity.IS_DELETED_ATTRIBUTE));
        criteria.where(isNotDeletedPredicate);
        return criteria;
    }
}
