package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;
import java.util.List;

public interface CertificateService {
    CertificateDTO getById(Long id);

    CertificateDTO create(CertificateDTO certificateDTO);

    boolean remove(Long id);

    //create/update
    CertificateDTO update(CertificateDTO certificateDTO);

    List<CertificateDTO> executeQueryDTO(CertificatePageQueryDTO queryDTO);
}
