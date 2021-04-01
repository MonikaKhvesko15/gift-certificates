package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

public class CertificateRepository extends AbstractRepository<Certificate> {

    private static final String GIFT_CERTIFICATES_TABLE_NAME = "gift_certificates";
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES\n" +
            "(?, ?, ?, ?, ?, ?);";

    protected CertificateRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return GIFT_CERTIFICATES_TABLE_NAME;
    }

    @Override
    protected RowMapper<Certificate> getRowMapper() {
        return new CertificateMapper();
    }

    @Override
    public Long save(Certificate certificate) {
        return insertData(INSERT_GIFT_CERTIFICATE_QUERY, extractFields(certificate));
    }

    private List<Object> extractFields(Certificate certificate) {
        return Arrays.asList(
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate());
    }

    public void update(Certificate t) {

    }
}
