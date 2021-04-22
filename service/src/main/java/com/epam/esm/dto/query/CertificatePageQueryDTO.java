package com.epam.esm.dto.query;

import javax.validation.constraints.Size;

public class CertificatePageQueryDTO extends PageQueryDTO {
    @Size(max = 50)
    private String tagName;

    public CertificatePageQueryDTO() {
    }

    public CertificatePageQueryDTO(String tagName, String context, String sortBy, String order) {
        super(sortBy, order, context);
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public String toString() {
        return "CertificatePageQueryDTO{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
}
