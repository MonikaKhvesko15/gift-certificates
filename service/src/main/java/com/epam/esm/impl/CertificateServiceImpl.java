package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.entity.Certificate;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.specification.CertificateIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final Repository<Certificate> certificateRepository;

    @Autowired
    public CertificateServiceImpl(Repository<Certificate> certificateRepository) {
        this.certificateRepository = certificateRepository;
    }


    @Override
    public List<Certificate> getAll() {
        return certificateRepository.queryForListResult(new CertificateAllSpecification());
    }

    @Override
    public Certificate getById(String id) {
        return certificateRepository.queryForSingleResult(new CertificateIdSpecification(id)).get();
    }

    @Override
    public Long create(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }


}
