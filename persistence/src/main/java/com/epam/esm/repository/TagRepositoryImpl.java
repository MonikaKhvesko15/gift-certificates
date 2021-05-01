package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> {
    private static final Class<Tag> tagClass = Tag.class;

    public TagRepositoryImpl(EntityManager entityManager) {
        super(entityManager, tagClass);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tag model = entityManager.find(tagClass, id);
        entityManager.remove(model);
    }

    @Override
    public Optional<Tag> getByName(String name) {
        CriteriaQuery<Tag> criteria = builder.createQuery(tagClass);
        Root<Tag> entityRoot = criteria.from(tagClass);
        criteria.select(entityRoot)
                .where(builder.equal(entityRoot.get("name"), name));
        return findOrEmpty(() ->
                entityManager
                        .createQuery(criteria)
                        .getSingleResult());
    }

    @Override
    public Optional<Tag> getById(Long id) {
        return Optional.ofNullable(entityManager.find(tagClass, id));
    }
}

