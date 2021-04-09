package com.epam.esm.specification;

public class CertificateAllSpecification implements SqlSpecification {
    private final String whereSQL;
    private final String orderBySQL;

    public CertificateAllSpecification(String whereSQL, String orderBySQL) {
        this.whereSQL = whereSQL;
        this.orderBySQL = orderBySQL;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM fn_getCertificatesWithTags()" + whereSQL + orderBySQL + ";";
    }
}