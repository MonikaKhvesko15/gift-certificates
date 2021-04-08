package com.epam.esm.specification;

public class CertificateByPartOfContextSpecification implements SqlSpecification {
    private final String context;

    public CertificateByPartOfContextSpecification(String context) {
        this.context = context;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM fn_getCertificateByPartOfContext ('%" + context + "%')";
    }
}
