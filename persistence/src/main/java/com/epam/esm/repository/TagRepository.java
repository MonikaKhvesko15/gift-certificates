package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class TagRepository extends AbstractRepository<Tag> {

    private static final String TAGS_TABLE_NAME = "tags";
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags(name) VALUES (?);";

    protected TagRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return TAGS_TABLE_NAME;
    }

    @Override
    protected RowMapper<Tag> getRowMapper() {
        return new TagMapper();
    }

    @Override
    public Long save(Tag tag) {
        String name = tag.getName();
        return insertData(INSERT_TAG_QUERY, name);
    }
}
