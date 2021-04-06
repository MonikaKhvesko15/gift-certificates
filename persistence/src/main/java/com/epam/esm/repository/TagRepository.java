package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

@Repository
public class TagRepository extends AbstractRepository<Tag> {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags (name) VALUES (?);";

    @Autowired
    protected TagRepository(DataSource dataSource) {
        super(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
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
        String name = tag.getName();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_TAG_QUERY);
            ps.setString(1, name);
            return ps;
        }, keyHolder);

        Long id = (Long) keyHolder.getKey();
        return getById(id);
    }

    @Override
    public Tag update(Tag tag) {
        return null;
    }
}

