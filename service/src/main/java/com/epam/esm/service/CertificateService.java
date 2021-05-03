package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CertificateService {
    CertificateDTO getById(Long id);

    CertificateDTO create(CertificateDTO certificateDTO);

    void remove(Long id);

    CertificateDTO update(Long id, CertificateDTO certificate);

    Page<CertificateDTO> findByParams(CertificatePageQueryDTO queryDTO, Pageable pageable);
}
