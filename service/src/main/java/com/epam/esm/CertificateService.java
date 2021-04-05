package com.epam.esm;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public interface CertificateService {
    List<CertificateDto> getAll();

    CertificateDto getById(String id);

    CertificateDto create(CertificateDto certificate);

    boolean remove(Long id);
}
