package com.epam.esm.event;

import com.epam.esm.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

public class EventListener {

    @PostUpdate
    public void handlePreUpdate(BaseEntity entity) {
        commitAction(new Event(OperationType.UPDATE, entity));
    }

    @PreRemove
    public void handlePreRemove(BaseEntity entity) {
        commitAction(new Event(OperationType.REMOVE, entity));
    }

    @PostPersist
    public void handlePrePersist(BaseEntity entity) {
        commitAction(new Event(OperationType.PERSIST, entity));
    }

    public void commitAction(Event event) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(event);
    }
}
