package com.epam.esm.specification;

public class TagsByCertificateIdSpecification implements SqlSpecification {
    private final Long certificateId;

    public TagsByCertificateIdSpecification(Long certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT tags.id, tags.name " +
                "FROM tags JOIN gift_certificates_tags ON tags.id = gift_certificates_tags.tag_id " +
                "WHERE gift_certificates_tags.gift_certificate_id = " + certificateId;
    }
}
