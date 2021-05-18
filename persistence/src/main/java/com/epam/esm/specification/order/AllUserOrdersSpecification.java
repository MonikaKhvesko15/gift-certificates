package com.epam.esm.specification.order;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Order;
import com.epam.esm.specification.CriteriaSpecification;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class AllUserOrdersSpecification implements CriteriaSpecification<Order> {
    private static final Class<Order> ORDER_CLASS = Order.class;
    private final Long userId;

    @Override
    public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Order> criteria = builder.createQuery(ORDER_CLASS);
        Root<Order> orderRoot = criteria.from(ORDER_CLASS);
        Predicate ordersByUserIdPredicate = builder.equal(orderRoot.get(Order.USER_ID_ATTRIBUTE), userId);
        Predicate isNotDeletedPredicate = builder.isFalse(orderRoot.get(BaseEntity.IS_DELETED_ATTRIBUTE));
        criteria.select(orderRoot).distinct(true).where(ordersByUserIdPredicate,
                isNotDeletedPredicate);
        return criteria;
    }
}
