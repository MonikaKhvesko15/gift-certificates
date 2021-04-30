package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;

import java.util.List;

public interface CertificateService {
    CertificateDTO getById(Long id);

    CertificateDTO create(CertificateDTO certificateDTO);

    void remove(Long id);

    List<CertificateDTO> findAll();

    CertificateDTO update(Long id, CertificateDTO certificate);

    List<CertificateDTO> findByParams(CertificatePageQueryDTO queryDTO);
}
