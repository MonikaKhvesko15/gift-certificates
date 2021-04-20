package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;

import java.util.List;

public interface CertificateService {
    CertificateDTO getById(Long id);

    CertificateDTO create(CertificateDTO certificateDTO);

    boolean remove(Long id);

    CertificateDTO update(Long id, CertificateDTO certificateDTO);

    List<CertificateDTO> executeQuery(CertificatePageQueryDTO queryDTO);
}
