package com.epam.esm.dto.query;

import javax.validation.constraints.Size;

public class PageQueryDTO {

    private String sortBy="";

    private String order="";

    @Size(max = 255)
    private String context;

    public PageQueryDTO() {
    }

    public PageQueryDTO(String sortBy, String order, String context) {
        this.sortBy = sortBy;
        this.order = order;
        this.context = context;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getOrder() {
        return order;
    }

    public String getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "PageQueryDTO{" +
                "sortBy='" + sortBy + '\'' +
                ", order='" + order + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
