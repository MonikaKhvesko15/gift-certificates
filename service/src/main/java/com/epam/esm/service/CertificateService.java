package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;

public interface CertificateService {
    CertificateDTO getById(Long id);

    CertificateDTO create(CertificateDTO certificateDTO);

    void remove(Long id);

    CertificateDTO update(Long id, CertificateDTO certificate);

    PageDTO<CertificateDTO> findByParams(CertificatePageQueryDTO queryDTO, PageRequestDTO pageRequestDTO);

    CertificateDTO updateDuration(Long id, Integer duration);
}
