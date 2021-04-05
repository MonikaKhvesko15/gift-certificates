package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.specification.TagIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepository extends AbstractRepository<Tag> {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags(name) VALUES (?);";

    @Autowired
    protected TagRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
        String name = tag.getName();
        Long id = insertData(INSERT_TAG_QUERY, name);
        return queryForSingleResult(new TagIdSpecification(id.toString())).orElseThrow(EntityNotFoundException::new);
    }
}
