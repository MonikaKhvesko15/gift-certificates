package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate.Columns;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<Certificate> implements CertificateRepository {
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates (name, " +
            "description, price, duration, create_date, last_update_date) VALUES\n" +
            "(:name, :description, :price, :duration, now(),now());";
    private static final String DELETE_TAGS_QUERY = "DELETE FROM gift_certificates_tags " +
            "WHERE gift_certificate_id= :gift_certificate_id";
    private static final String UPDATE_GIFT_CERTIFICATE_QUERY = "UPDATE  gift_certificates " +
            "SET (name, description, price, duration, last_update_date) = " +
            "(:name, :description, :price, :duration ,now()) WHERE id=:id";
    private static final String DELETE_CERTIFICATE_QUERY = "UPDATE  gift_certificates SET isDeleted = 1 WHERE id=:id";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM gift_certificates WHERE isDeleted = 0 AND id = :id";
    private static final int NUMBER_UPDATED_ROWS = 1;

    @Autowired
    public CertificateRepositoryImpl(DataSource dataSource) {
        super(dataSource);
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
        Map<String, Object> mapKey = keyHolder.getKeys();
        assert mapKey != null;
        Long id = (Long) mapKey.getOrDefault(Columns.ID.getColumn(), null);
        certificate.setId(id);
        return certificate;
    }

    @Override
    public Certificate update(Long id, Certificate certificate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(
                Columns.ID.getColumn(), id)
                .addValue(Columns.NAME.getColumn(), certificate.getName())
                .addValue(Columns.DESCRIPTION.getColumn(), certificate.getDescription())
                .addValue(Columns.PRICE.getColumn(), certificate.getPrice())
                .addValue(Columns.DURATION.getColumn(), certificate.getDuration());

        template.update(UPDATE_GIFT_CERTIFICATE_QUERY, params);
        certificate.setId(id);
        return certificate;
    }

    @Override
    public void deleteCertificateTags(Long certificateId) {
        MapSqlParameterSource tagParams = new MapSqlParameterSource();
        tagParams.addValue(Columns.GIFT_CERTIFICATE_ID.getColumn(), certificateId);
        template.update(DELETE_TAGS_QUERY, tagParams);
    }

    @Override
    public boolean deleteById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Columns.ID.getColumn(), id);
        return template.update(DELETE_CERTIFICATE_QUERY, params) == NUMBER_UPDATED_ROWS;
    }

    @Override
    public Optional<Certificate> getById(Long id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue(Columns.ID.getColumn(), id);
        return template.query(GET_BY_ID_QUERY, param, getRowMapper()).stream().findAny();
    }
}
