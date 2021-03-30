package com.epam.esm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tag implements Serializable {
    private Long id;
    private String name;
    private List<Certificate> certificates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public boolean addCertificate(Certificate certificate) {
        if (certificates == null) {
            certificates = new ArrayList<>();
            certificates.add(certificate);
            return true;
        } else {
            if (certificates.contains(certificate)) {
                return false;
            }
        }
        certificates.add(certificate);
        return true;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", certificates=" + certificates +
                '}';
    }
}
