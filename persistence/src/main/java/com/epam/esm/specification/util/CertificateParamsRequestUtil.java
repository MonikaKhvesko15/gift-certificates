package com.epam.esm.specification.util;

import org.apache.commons.lang3.StringUtils;

public class CertificateParamsRequestUtil {
    private static final String DATE_SORT_TYPE = "DATE";
    private static final String NAME_SORT_TYPE = "NAME";
    private String sortBy;
    private final String order;

    public CertificateParamsRequestUtil(String sortBy, String order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getSortQueryWithParams() {
        String orderBySQL = " ORDER BY ";
        if (sortBy.equalsIgnoreCase(DATE_SORT_TYPE)) {
            sortBy = "create_date";
        }
        if (sortBy.equalsIgnoreCase(NAME_SORT_TYPE)) {
            sortBy = "name";
        }
        if (StringUtils.isNoneEmpty(sortBy)) {
            if (StringUtils.isNoneEmpty(order)) {
                orderBySQL = orderBySQL + sortBy + " " + order.toUpperCase() + "";
            } else {
                orderBySQL = orderBySQL + sortBy + " " + "ASC";
            }
        }

        if (orderBySQL.equals(" ORDER BY ")) {
            orderBySQL = "";
        }

        return orderBySQL;
    }

}

