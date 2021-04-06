package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate.Columns;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Set;

@Repository
public class CertificateRepository extends AbstractRepository<Certificate> {
    private final NamedParameterJdbcTemplate template;
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES\n" +
            "(:name, :description, :price, :duration, now(),now());";
    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id) VALUES (:gift_certificate_id, :tag_id);";
    private static final String UPDATE_GIFT_CERTIFICATE_QUERY = "UPDATE  gift_certificates SET (name, description, price, duration, last_update_date)= (:name, :description, :price, :duration ,now()) WHERE id=:id";
    private static final String DELETE_QUERY = "UPDATE  gift_certificates SET (isDeleted) = 1 WHERE id=:id";

    @Autowired
    protected CertificateRepository(DataSource dataSource) {
        super(dataSource);
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    protected String getTableName() {
        return Certificate.GIFT_CERTIFICATES_TABLE_NAME;
    }

    @Override
    protected RowMapper<Certificate> getRowMapper() {
        return new CertificateMapper();
    }

    @Override
    public Certificate save(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Columns.NAME.getColumn(), certificate.getName())
                .addValue(Columns.DESCRIPTION.getColumn(), certificate.getDescription())
                .addValue(Columns.PRICE.getColumn(), certificate.getPrice())
                .addValue(Columns.DURATION.getColumn(), certificate.getDuration());
        template.update(INSERT_GIFT_CERTIFICATE_QUERY, params, keyHolder);

        Set<Tag> tags = certificate.getTags();
        Long certificateId = certificate.getId();
        MapSqlParameterSource tagParams = new MapSqlParameterSource();
        tagParams.addValue("gift_certificate_id", certificateId);
        tags.forEach(tag -> tagParams.addValue("tag_id", tag.getId()));
        template.update(ADD_TAGS_QUERY, tagParams);

        Long id = (Long) keyHolder.getKey();
        return getById(id);
    }

    public Certificate update(Certificate certificate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Columns.ID.getColumn(), certificate.getId())
                .addValue(Columns.NAME.getColumn(), certificate.getName())
                .addValue(Columns.DESCRIPTION.getColumn(), certificate.getDescription())
                .addValue(Columns.PRICE.getColumn(), certificate.getPrice())
                .addValue(Columns.DURATION.getColumn(), certificate.getDuration());
        template.update(UPDATE_GIFT_CERTIFICATE_QUERY, params);

        Set<Tag> tags = certificate.getTags();
        Long certificateId = certificate.getId();
        MapSqlParameterSource tagParams = new MapSqlParameterSource();
        tagParams.addValue("gift_certificate_id", certificateId);
        tags.forEach(tag -> tagParams.addValue("tag_id", tag.getId()));
        template.update(ADD_TAGS_QUERY, tagParams);

        return getById(certificateId);
    }

    @Override
    public boolean deleteById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Columns.ID.getColumn(), id);
        return template.update(DELETE_QUERY, params) == 1;
    }
}
