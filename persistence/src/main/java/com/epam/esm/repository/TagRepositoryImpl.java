package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.specification.TagsByCertificateIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags (name) VALUES (?)";
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM tags WHERE name = ?";


    @Autowired
    protected TagRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    protected String getTableName() {
        ;
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
        PreparedStatementCreator statementCreator = connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_TAG_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, name);
            return statement;
        };

        jdbcTemplate.update(statementCreator, keyHolder);

        Long id = (Long) keyHolder.getKeys().get("id");

        return getById(id);
    }

    @Override
    public Tag getByName(String name) {
        //todo: refactoring deprecated methods
        if (jdbcTemplate.query(GET_BY_NAME_QUERY, new Object[]{name}, getRowMapper()).isEmpty()) {
            return null;
        } else {
            return (Tag) jdbcTemplate.query(GET_BY_NAME_QUERY, new Object[]{name}, getRowMapper()).get(0);
        }
    }

    @Override
    public Set<Tag> getTagsByCertificateId(Long certificateId) {
        SqlSpecification specification = new TagsByCertificateIdSpecification(certificateId);
        Set<Tag> tags = new HashSet<>(query(specification));
        return tags;
    }
}

