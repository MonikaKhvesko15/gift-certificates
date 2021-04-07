package com.epam.esm.dto.query;

import java.util.Objects;

public class PageQueryDTO {
    private String sortBy;
    private String order;

    public PageQueryDTO() {
    }

    public PageQueryDTO(String sortBy, String order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageQueryDTO that = (PageQueryDTO) o;
        return Objects.equals(sortBy, that.sortBy) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortBy, order);
    }

    @Override
    public String toString() {
        return "PageRequestDTO{" +
                "sortBy='" + sortBy + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
