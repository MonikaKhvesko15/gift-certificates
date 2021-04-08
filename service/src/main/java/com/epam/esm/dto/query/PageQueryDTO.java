package com.epam.esm.dto.query;

import java.util.Objects;

public class PageQueryDTO {
    private String sortBy;
    private String order;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageQueryDTO that = (PageQueryDTO) o;
        return Objects.equals(sortBy, that.sortBy) &&
                Objects.equals(order, that.order) &&
                Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortBy, order, context);
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
