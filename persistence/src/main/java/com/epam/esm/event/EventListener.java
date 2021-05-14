package com.epam.esm.event;

import com.epam.esm.entity.BaseEntity;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

//@AllArgsConstructor
public class EventListener {
    //todo: refactor
//    @PersistenceContext
//    private final EntityManager entityManager;

    @PostUpdate
    public void handlePreUpdate(BaseEntity entity) {
        commitAction(Event.updateFor(entity));
    }

    @PreRemove
    public void handlePreRemove(BaseEntity entity) {
        commitAction(Event.removeFor(entity));
    }

    @PostPersist
    public void handlePrePersist(BaseEntity entity) {
        commitAction(Event.persistFor(entity));
    }

    public void commitAction(Event event) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(event);
    }
}
