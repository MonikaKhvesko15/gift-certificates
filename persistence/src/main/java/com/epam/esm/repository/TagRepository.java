package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;

public class TagRepository extends AbstractRepository<Tag> {


    private static final String TAGS_TABLE_NAME = "tags";
    private static final String INSERT_TAG_QUERY = "INSERT INTO tag (name) VALUES (?);";

    protected TagRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Tag add(Tag tag) {
        String name = tag.getName();
        Integer key = insertData(INSERT_TAG_QUERY, name);
        return queryForSingleResult();
    }

    @Override
    protected String getTableName() {
        return TAGS_TABLE_NAME;
    }

}
