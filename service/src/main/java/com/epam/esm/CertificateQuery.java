package com.epam.esm;

import java.util.Objects;

public class CertificateQuery {
    private String tagName;
    private String name;
    private String description;
    private String sortDate;
    private String sortName;

    public CertificateQuery() {
    }

    public CertificateQuery(String tagName, String name, String description, String sortDate, String sortName) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sortDate = sortDate;
        this.sortName = sortName;
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

    public String getSortDate() {
        return sortDate;
    }

    public String getSortName() {
        return sortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateQuery that = (CertificateQuery) o;
        return Objects.equals(tagName, that.tagName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(sortDate, that.sortDate) &&
                Objects.equals(sortName, that.sortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, name, description, sortDate, sortName);
    }

    @Override
    public String toString() {
        return "CertificateQuery{" +
                "tagName='" + tagName + '\'' +
                ", partCertificateName='" + name + '\'' +
                ", partCertificateDescription='" + description + '\'' +
                ", sortDate='" + sortDate + '\'' +
                ", sortCertificateName='" + sortName + '\'' +
                '}';
    }
}
