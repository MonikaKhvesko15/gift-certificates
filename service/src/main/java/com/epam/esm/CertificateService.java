package com.epam.esm;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.query.CertificateQuery;
import com.epam.esm.dto.query.PageQueryDTO;

import java.util.List;

public interface CertificateService {
    List<CertificateDTO> getAll();

    CertificateDTO getById(Long id);

    CertificateDTO create(CertificateDTO certificateDTO);

    boolean remove(Long id);

    CertificateDTO update(CertificateDTO certificateDTO);

    List<CertificateDTO> findByQuery(CertificateQuery certificateQuery);

    List<CertificateDTO> sortByQuery(PageQueryDTO query);
}
