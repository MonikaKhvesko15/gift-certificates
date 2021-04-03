package com.epam.esm.specification;


public class CertificateIdSpecification implements SqlSpecification{
    private final String id;

    public CertificateIdSpecification(String id) {
        this.id = id;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM gift_certificates WHERE id=" + id;
    }
}
