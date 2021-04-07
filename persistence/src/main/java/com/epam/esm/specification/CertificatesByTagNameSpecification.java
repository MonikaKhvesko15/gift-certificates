package com.epam.esm.specification;

public class CertificatesByTagNameSpecification implements SqlSpecification {
    private final String tagName;


    public CertificatesByTagNameSpecification(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM gift_certificates JOIN gift_certificates_tags \n" +
                "ON gift_certificates_tags.gift_certificate_id = gift_certificates.id \n" +
                "JOIN tags\n" +
                "ON gift_certificates_tags.tag_id = tags.id\n" +
                "WHERE tags.name = '" + tagName + "'";
    }
}
