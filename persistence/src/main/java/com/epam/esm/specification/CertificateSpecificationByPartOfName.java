package com.epam.esm.specification;

public class CertificateSpecificationByPartOfName implements SqlSpecification{
    private final String name;

    public CertificateSpecificationByPartOfName(String name) {
        this.name = name;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM gift_certificates\n" +
                "WHERE gift_certificates.name LIKE '%"+name+"%'";
    }
}
