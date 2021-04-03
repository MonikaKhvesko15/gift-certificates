package com.epam.esm.specification;

public class CertificateAllSpecification implements SqlSpecification{

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM gift_certificates;";
    }
}