package com.epam.esm.specification;

public class CertificateByPartOfDescriptionSpecification implements SqlSpecification {
    private final String description;

    public CertificateByPartOfDescriptionSpecification(String description) {
        this.description = description;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM gift_certificates\n" +
                "WHERE gift_certificates.description LIKE '%" + description + "%'";
    }
}
