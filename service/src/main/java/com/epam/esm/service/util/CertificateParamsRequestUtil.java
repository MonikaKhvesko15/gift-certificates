package com.epam.esm.service.util;

import com.epam.esm.dto.query.CertificatePageQueryDTO;
import org.apache.commons.lang3.StringUtils;

public class CertificateParamsRequestUtil {
    private final CertificatePageQueryDTO queryDTO;

    public CertificateParamsRequestUtil(CertificatePageQueryDTO queryDTO) {
        this.queryDTO = queryDTO;
    }

    public String getWhereQueryWithParams() {
        String whereSQL = " WHERE";
        String tagName = queryDTO.getTagName();
        if (StringUtils.isNoneEmpty(tagName)) {
            whereSQL = whereSQL + " tagName = '" + tagName + "'";
        }

        String context = queryDTO.getContext();
        if (StringUtils.isNoneEmpty(context)) {
            if (StringUtils.isNoneEmpty(tagName)) {
                whereSQL = whereSQL + " AND ";
            }
            whereSQL = whereSQL + " (name SIMILAR TO '%" + context + "%' OR description SIMILAR TO '%" + context + "%')";
        }

        if (whereSQL.equals(" WHERE")) {
            whereSQL = "";
        }
        return whereSQL;
    }

    public String getSortQueryWithParams() {
        String orderBySQL = " ORDER BY ";
        String sortBy = queryDTO.getSortBy();
        if (sortBy.equalsIgnoreCase("DATE")) {
            sortBy = "create_date";
        }
        if (sortBy.equalsIgnoreCase("NAME")) {
            sortBy = "name";
        }
        String order = queryDTO.getOrder();
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

