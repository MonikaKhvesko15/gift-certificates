package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;

public interface CertificateRepository extends Repository<Certificate> {
    Certificate update(Long id, Certificate certificate);

    void deleteCertificateTags(Long certificateId);
}
