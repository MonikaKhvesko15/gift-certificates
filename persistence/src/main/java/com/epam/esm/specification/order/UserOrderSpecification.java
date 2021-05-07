package com.epam.esm.specification.order;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.specification.CriteriaSpecification;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class UserOrderSpecification implements CriteriaSpecification<Order> {
    private final User user;
    private final Long orderId;

    @Override
    public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);
        Predicate isNotDeletedPredicate = builder.isFalse(orderRoot.get("isDeleted"));
        Predicate orderByUserIdPredicate = builder.equal(orderRoot.get("user"), user);
        Predicate orderIdPredicate = builder.equal(orderRoot.get("id"), orderId);
        criteria.select(orderRoot).distinct(true).where(isNotDeletedPredicate,
                orderByUserIdPredicate, orderIdPredicate);
        return criteria;
    }
}
