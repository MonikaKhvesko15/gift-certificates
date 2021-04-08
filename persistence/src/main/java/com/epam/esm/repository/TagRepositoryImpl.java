package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag.Columns;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.specification.TagsByCertificateIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags (name) VALUES (:name)";
    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id) VALUES (:gift_certificate_id, :tag_id)";

    @Autowired
    protected TagRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return Tag.TAGS_TABLE_NAME;
    }

    @Override
    protected RowMapper<Tag> getRowMapper() {
        return new TagMapper();
    }

    @Override
    public Tag save(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Columns.NAME.getColumn(), tag.getName());
        template.update(INSERT_TAG_QUERY, params, keyHolder);
        Long id = (Long) keyHolder.getKeys().get("id");
        return getById(id);
    }

    @Override
    public Set<Tag> getTagsByCertificateId(Long certificateId) {
        SqlSpecification specification = new TagsByCertificateIdSpecification(certificateId);
        return new HashSet<>(query(specification));
    }

    @Override
    public Set<Tag> createNewTags(Set<Tag> tags) {
        Set<Tag> newTags = findNewTags(tags);
        newTags.forEach(this::save);
        return newTags;
    }

    private Set<Tag> findNewTags(Set<Tag> tags) {
                Set<Tag> newTags = new HashSet<>();
        tags.forEach(tag -> {
            if (getByName(tag.getName()) == null) {
                newTags.add(tag);
            }
        });
        return newTags;
    }

    @Override
    public Set<Tag> createCertificateTags(Certificate certificate) {
        Long certificateId = certificate.getId();
        Set<Tag> tags = certificate.getTags();

        tags.forEach(tag -> {
                    MapSqlParameterSource tagParams = new MapSqlParameterSource();
                    tagParams.addValue("gift_certificate_id", certificateId);
                    tagParams.addValue("tag_id", getByName(tag.getName()).getId());
                    template.update(ADD_TAGS_QUERY, tagParams);
                }
        );
        return tags;
    }
}

