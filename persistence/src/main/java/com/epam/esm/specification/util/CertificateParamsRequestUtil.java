package com.epam.esm.specification.util;

import org.apache.commons.lang3.StringUtils;

public class CertificateParamsRequestUtil {
    private String sortBy;
    private final String order;

    public CertificateParamsRequestUtil(String sortBy, String order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getSortQueryWithParams() {
        String orderBySQL = " ORDER BY ";
        if (sortBy.equalsIgnoreCase("DATE")) {
            sortBy = "create_date";
        }
        if (sortBy.equalsIgnoreCase("NAME")) {
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

