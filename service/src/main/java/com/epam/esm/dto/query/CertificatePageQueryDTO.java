package com.epam.esm.dto.query;

public class CertificatePageQueryDTO extends PageQueryDTO {
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
