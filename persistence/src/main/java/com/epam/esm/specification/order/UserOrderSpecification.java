package com.epam.esm.specification.order;

import com.epam.esm.entity.BaseEntity;
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
    private static final Class<Order> ORDER_CLASS = Order.class;
    private final User user;
    private final Long orderId;

    @Override
    public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Order> criteria = builder.createQuery(ORDER_CLASS);
        Root<Order> orderRoot = criteria.from(ORDER_CLASS);
        Predicate isNotDeletedPredicate = builder.isFalse(orderRoot.get(BaseEntity.IS_DELETED_ATTRIBUTE));

        Predicate orderByUserIdPredicate = builder.equal(orderRoot.get(Order.USER_ATTRIBUTE), user);
        Predicate orderIdPredicate = builder.equal(orderRoot.get(BaseEntity.ID_ATTRIBUTE), orderId);
        criteria.select(orderRoot).distinct(true).where(isNotDeletedPredicate,
                orderByUserIdPredicate, orderIdPredicate);
        return criteria;
    }
}
