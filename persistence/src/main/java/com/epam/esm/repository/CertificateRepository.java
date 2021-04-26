package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;

import java.util.Optional;

public interface CertificateRepository extends Repository<Certificate> {
    Optional<Certificate> update(Long id, Certificate certificate);

    void deleteCertificateTags(Long certificateId);
}
