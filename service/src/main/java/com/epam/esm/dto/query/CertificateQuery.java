package com.epam.esm.dto.query;

import java.util.Objects;

public class CertificateQuery {
    private String tagName;
    private String name;
    private String description;

    public CertificateQuery() {
    }

    public CertificateQuery(String tagName, String name, String description) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
    }

    public String getTagName() {
        return tagName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateQuery that = (CertificateQuery) o;
        return Objects.equals(tagName, that.tagName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, name, description);
    }

    @Override
    public String toString() {
        return "CertificateQuery{" +
                "tagName='" + tagName + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
