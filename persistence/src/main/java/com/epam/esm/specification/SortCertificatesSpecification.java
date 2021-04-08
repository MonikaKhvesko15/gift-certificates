package com.epam.esm.specification;

public class SortCertificatesSpecification implements SqlSpecification {
    private final String sortBy;
    private final String order;

    public SortCertificatesSpecification(String sortBy, String order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM fn_sortCertificates ('" + sortBy + "','" + order + "')";
    }
}
