package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate.Columns;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.specification.SqlSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<Certificate> implements CertificateRepository {

    private final NamedParameterJdbcTemplate template;
    private final TagRepository tagRepository;
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES\n" +
            "(:name, :description, :price, :duration, now(),now());";
    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id) VALUES (:gift_certificate_id, :tag_id)";
    private static final String DELETE_TAGS_QUERY = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id= :gift_certificate_id";
    private static final String UPDATE_GIFT_CERTIFICATE_QUERY = "UPDATE  gift_certificates SET (name, description, price, duration, last_update_date)= (:name, :description, :price, :duration ,now()) WHERE id=:id";
    private static final String DELETE_CERTIFICATE_QUERY = "UPDATE  gift_certificates SET isDeleted = 1 WHERE id=:id";

    @Autowired
    protected CertificateRepositoryImpl(DataSource dataSource, TagRepository tagRepository) {
        super(dataSource);
        template = new NamedParameterJdbcTemplate(dataSource);
        this.tagRepository = tagRepository;
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
        certificate.setId(id);

        Set<Tag> tags = certificate.getTags();

        if (!tags.isEmpty()) {
            createNewTags(tags);
            createCertificateRefsToTags(certificate);
        }

        return getById(id);
    }

    @Override
    public Set<Tag> createNewTags(Set<Tag> tags) {
        Set<Tag> newTags = findNewTags(tags);
        newTags.forEach(tagRepository::save);
        return newTags;
    }

    @Override
    public Set<Tag> findNewTags(Set<Tag> tags) {
        Set<Tag> newTags = new HashSet<>();
        tags.forEach(tag -> {
            if (tagRepository.getByName(tag.getName()) == null) {
                newTags.add(tag);
            }
        });
        return newTags;
    }

    @Override
    public Set<Tag> createCertificateRefsToTags(Certificate certificate) {
        Long certificateId = certificate.getId();
        Set<Tag> tags = certificate.getTags();

        tags.forEach(tag -> {
                    MapSqlParameterSource tagParams = new MapSqlParameterSource();
                    tagParams.addValue("gift_certificate_id", certificateId);
                    tagParams.addValue("tag_id", tagRepository.getByName(tag.getName()).getId());
                    template.update(ADD_TAGS_QUERY, tagParams);
                }
        );
        return tags;
    }

    @Override
    public void deleteOldCertificateRefsToTags(Long certificateId) {
        MapSqlParameterSource tagParams = new MapSqlParameterSource();
        tagParams.addValue("gift_certificate_id", certificateId);
        template.update(DELETE_TAGS_QUERY, tagParams);
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

        Set<Tag> tags = certificate.getTags();
        createNewTags(tags);
        deleteOldCertificateRefsToTags(certificateId);
        createCertificateRefsToTags(certificate);
        return getById(certificateId);
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
        List<Certificate> certificatesWithTags = new ArrayList<>();

        certificates.forEach(certificate -> {
            Set<Tag> tags = tagRepository.getTagsByCertificateId(certificate.getId());
            certificate.setTags(tags);
            certificatesWithTags.add(certificate);
        });

        return certificatesWithTags;
    }

}
