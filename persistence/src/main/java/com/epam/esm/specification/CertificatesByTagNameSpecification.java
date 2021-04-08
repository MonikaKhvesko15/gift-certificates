package com.epam.esm.specification;

public class CertificatesByTagNameSpecification implements SqlSpecification {
    private final String tagName;


    public CertificatesByTagNameSpecification(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM fn_getCertificateByTagName ('" + tagName + "')";
    }
}
