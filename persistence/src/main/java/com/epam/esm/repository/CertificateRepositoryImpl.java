package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate.Columns;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.specification.SqlSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<Certificate> implements CertificateRepository {
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES\n" +
            "(:name, :description, :price, :duration, now(),now());";
    private static final String DELETE_TAGS_QUERY = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id= :gift_certificate_id";
    private static final String UPDATE_GIFT_CERTIFICATE_QUERY = "UPDATE  gift_certificates SET (name, description, price, duration, last_update_date)= (:name, :description, :price, :duration ,now()) WHERE id=:id";
    private static final String DELETE_CERTIFICATE_QUERY = "UPDATE  gift_certificates SET isDeleted = 1 WHERE id=:id";

    @Autowired
    protected CertificateRepositoryImpl(DataSource dataSource) {
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
        Long id = (Long) keyHolder.getKeys().get("id");
        return getById(id);
    }

    @Override
    public Certificate update(Certificate certificate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(
                Columns.ID.getColumn(), certificate.getId())
                .addValue(Columns.NAME.getColumn(), certificate.getName())
                .addValue(Columns.DESCRIPTION.getColumn(), certificate.getDescription())
                .addValue(Columns.PRICE.getColumn(), certificate.getPrice())
                .addValue(Columns.DURATION.getColumn(), certificate.getDuration());

        template.update(UPDATE_GIFT_CERTIFICATE_QUERY, params);
        Long certificateId = certificate.getId();
        return getById(certificateId);
    }

    @Override
    public void deleteCertificateTags(Long certificateId) {
        MapSqlParameterSource tagParams = new MapSqlParameterSource();
        tagParams.addValue("gift_certificate_id", certificateId);
        template.update(DELETE_TAGS_QUERY, tagParams);
    }

    @Override
    public boolean deleteById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Columns.ID.getColumn(), id);
        return template.update(DELETE_CERTIFICATE_QUERY, params) == 1;
    }

    @Override
    public List<Certificate> query(SqlSpecification specification) {
        List<Certificate> certificates = super.query(specification);
//        List<Certificate> certificatesWithTags = new ArrayList<>();
//
//        certificates.forEach(certificate -> {
//            Set<Tag> tags = tagRepository.getTagsByCertificateId(certificate.getId());
//            certificate.setTags(tags);
//            certificatesWithTags.add(certificate);
//        });

        return certificates;
    }

}
