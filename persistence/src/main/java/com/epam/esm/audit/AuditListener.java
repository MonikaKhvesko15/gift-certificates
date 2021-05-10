package com.epam.esm.audit;

import javax.persistence.EntityManager;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

public class AuditListener {
    @PostUpdate
    public void handlePreUpdate(Object object) {
        logAction(Audit.updateFor(object));
    }

    @PreRemove
    public void handlePreRemove(Object object) {
        logAction(Audit.removeFor(object));
    }

    @PrePersist
    public void handlePrePersist(Object object) {
        logAction(Audit.persistFor(object));
    }

    public void logAction(Audit audit) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(audit);
    }
}
