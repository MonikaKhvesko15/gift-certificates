package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository{

    private static final String MOST_WIDELY_USED_TAG_PROCEDURE = "getMostWidelyUsedTag";
    private static final String VAR_USER_ID_PARAMETER = "var_user_id";

    public TagRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Tag.class);
    }

    @Override
    @Transactional
    public void delete(Tag tag) {
        entityManager.remove(entityManager.contains(tag) ? tag : entityManager.merge(tag));
    }

    @Override
    public Optional<Tag> getByName(String name) {
        criteria.select(entityRoot)
                .where(builder.equal(entityRoot.get(NAME_ATTRIBUTE), name));
        return findOrEmpty(() ->
                entityManager
                        .createQuery(criteria)
                        .getSingleResult());
    }

    @Override
    public Optional<Tag> getById(Long id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public Optional<Tag> getMostPopularTag(Long userId) {
        StoredProcedureQuery getMostWidelyUsedTag =
                entityManager.createNamedStoredProcedureQuery(MOST_WIDELY_USED_TAG_PROCEDURE);
        StoredProcedureQuery storedProcedure =
                getMostWidelyUsedTag.setParameter(VAR_USER_ID_PARAMETER, userId);
        Tag tag = null;
        if (CollectionUtils.isNotEmpty(storedProcedure.getResultList())) {
            tag = (Tag) storedProcedure.getSingleResult();
        }
        return Optional.ofNullable(tag);
    }
}

