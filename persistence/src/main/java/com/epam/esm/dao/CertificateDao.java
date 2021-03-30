package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateDao {
    List<Certificate> findAll();

    void insert(Certificate certificate);

    void update(Certificate certificate);

    void deleteById(Long id);
}
