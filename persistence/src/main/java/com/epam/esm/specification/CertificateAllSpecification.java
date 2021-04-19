package com.epam.esm.specification;

import com.epam.esm.specification.util.CertificateParamsRequestUtil;

public class CertificateAllSpecification implements SqlSpecification {
    private String tagName;
    private String context;
    private final CertificateParamsRequestUtil util;

    public CertificateAllSpecification(String tagName, String context, String sortBy, String order) {
        this.tagName = tagName;
        this.context = context;
        this.util = new CertificateParamsRequestUtil(sortBy, order);
    }

    @Override
    public String getSqlQuery() {
        String orderBySQL = util.getSortQueryWithParams();
        if (tagName != null) {
            tagName = "'" + tagName + "'";
        }
        if (context != null) {
            context = "'" + context + "'";
        }
        return "SELECT * FROM fn_getCertificatesWithTags(" + tagName + "," + context + ") " + orderBySQL + ";";
    }
}