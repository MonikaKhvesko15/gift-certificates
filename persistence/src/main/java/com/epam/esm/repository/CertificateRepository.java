package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate.Columns;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.specification.CertificateIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class CertificateRepository extends AbstractRepository<Certificate> {

    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES\n" +
            "(?, ?, ?, ?, ?, ?);";
    private static final String INSERT_TAG_CERTIFICATE_QUERY = "INSERT INTO gift_certificates_tags (gift_certificate_id,tag_id) VALUES\n" +
            "(?, ?);";

    @Autowired
    protected CertificateRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
        Long id = insertData(INSERT_GIFT_CERTIFICATE_QUERY, extractFields(certificate));
        Set<Tag> tags = certificate.getTags();
        Long certificateId = certificate.getId();
        tags.forEach(tag -> jdbcTemplate.update(INSERT_TAG_CERTIFICATE_QUERY, certificateId, tag.getId()));
        return queryForSingleResult(new CertificateIdSpecification(id.toString())).orElseThrow(EntityNotFoundException::new);
    }

//    public Certificate update(){
//
//    }

//    private List<Object> extractFields(Certificate certificate) {
//        return Arrays.asList(
//                certificate.getName(),
//                certificate.getDescription(),
//                certificate.getPrice(),
//                certificate.getDuration(),
//                certificate.getCreateDate(),
//                certificate.getLastUpdateDate());
//    }

    private Map<String, Object> extractFields(Certificate certificate) {
        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put(Columns.NAME.getColumn(), certificate.getName());
        fields.put(Columns.DESCRIPTION.getColumn(), certificate.getDescription());
        fields.put(Columns.PRICE.getColumn(), certificate.getPrice());
        fields.put(Columns.DURATION.getColumn(), certificate.getDuration());
        fields.put(Columns.CREATE_DATE.getColumn(), certificate.getCreateDate());
        fields.put(Columns.LAST_UPDATE_DATE.getColumn(), certificate.getLastUpdateDate());
        return fields;
    }
}
