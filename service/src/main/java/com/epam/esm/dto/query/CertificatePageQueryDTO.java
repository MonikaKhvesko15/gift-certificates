package com.epam.esm.dto.query;

import java.util.Objects;

public class CertificatePageQueryDTO extends PageQueryDTO{
    private String tagName;

    public CertificatePageQueryDTO() {
    }

    public CertificatePageQueryDTO(String tagName,String context, String sortBy, String order) {
        super(sortBy,order,context);
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CertificatePageQueryDTO that = (CertificatePageQueryDTO) o;
        return Objects.equals(tagName, that.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tagName);
    }

    @Override
    public String toString() {
        return "CertificatePageQueryDTO{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
}
