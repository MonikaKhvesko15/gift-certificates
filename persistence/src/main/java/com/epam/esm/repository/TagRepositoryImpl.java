package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag.Columns;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags (name) VALUES (:name)";
    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id) " +
            "VALUES (:gift_certificate_id, :tag_id)";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM tags WHERE id = :id";
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM tags WHERE name = :name";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM tags WHERE id = :id";

    @Autowired
    public TagRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        getByIdQuery = GET_BY_ID_QUERY;
        getByNameQuery = GET_BY_NAME_QUERY;
        deleteByIdQuery = DELETE_BY_ID_QUERY;
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
        Map<String, Object> mapKey = keyHolder.getKeys();
        assert mapKey != null;
        Long id = (Long) mapKey.getOrDefault(Certificate.Columns.ID.getColumn(), null);
        tag.setId(id);
        return tag;
    }

    @Override
    public void createCertificateTags(Long certificateId, Set<Tag> tags) {
        tags.forEach(tag -> {
                    MapSqlParameterSource tagParams = new MapSqlParameterSource();
                    tagParams.addValue(Certificate.Columns.GIFT_CERTIFICATE_ID.getColumn(), certificateId);
                    tagParams.addValue(Columns.TAG_ID.getColumn(), tag.getId());
                    template.update(ADD_TAGS_QUERY, tagParams);
                }
        );
    }
}

