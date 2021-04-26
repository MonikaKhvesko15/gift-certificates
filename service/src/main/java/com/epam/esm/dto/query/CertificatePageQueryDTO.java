package com.epam.esm.dto.query;

import com.epam.esm.validator.OrderType;
import com.epam.esm.validator.SortType;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class CertificatePageQueryDTO implements Serializable {
    @Size(max = 255)
    private String context = "";

    @Size(max = 50)
    private String tagName = "";

    @SortType
    private String sortBy = "name";

    @OrderType
    private String order = "asc";

    public CertificatePageQueryDTO(String context, String tagName, String sortBy, String order) {
        this.context = context;
        this.tagName = tagName;
        this.sortBy = sortBy;
        this.order = order;
    }

    public CertificatePageQueryDTO() {
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "CertificatePageQueryDTO{" +
                "context='" + context + '\'' +
                ", tagName='" + tagName + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
