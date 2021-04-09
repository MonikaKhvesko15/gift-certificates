package com.epam.esm.service.impl;

import com.epam.esm.service.CertificateService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.CertificateConverterDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.specification.CertificateByPartOfContextSpecification;
import com.epam.esm.specification.CertificatesByTagNameSpecification;
import com.epam.esm.specification.SortCertificatesSpecification;
import com.epam.esm.specification.SqlSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;


    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public CertificateDTO getById(Long id) {
        Certificate certificate = certificateRepository.getById(id);
        addTagsToCertificate(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        checkTags(certificate);
        certificate = certificateRepository.save(certificate);
        tagRepository.createCertificateTags(certificate);
        addTagsToCertificate(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        certificateRepository.deleteCertificateTags(certificate.getId());
        checkTags(certificate);
        certificate = certificateRepository.update(certificate);
        tagRepository.createCertificateTags(certificate);
        addTagsToCertificate(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    private void checkTags(Certificate certificate) {
        Set<Tag> tags = certificate.getTags();
        if (!tags.isEmpty()) {
            tagRepository.createNewTags(tags);
        }
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }

    @Override
    public List<CertificateDTO> executeQueryDTO(CertificatePageQueryDTO queryDTO) {

        String whereSQL = " WHERE";
        String tagName = queryDTO.getTagName();
        if (StringUtils.isNoneEmpty(tagName)) {
            whereSQL = whereSQL + " tagName = '" + tagName + "'";
        }

        String context = queryDTO.getContext();
        if (StringUtils.isNoneEmpty(context)) {
            whereSQL = whereSQL + " name LIKE '%" + context + "%' OR description LIKE '%" + context + "%'";
        }
        if (whereSQL.equals(" WHERE")) {
            whereSQL = "";
        }

        String orderBySQL = " ORDER BY ";
        String sortBy = queryDTO.getSortBy();
        if (sortBy.equals("date")) {
            sortBy = "create_date";
        }
        String order = queryDTO.getOrder();
        if (StringUtils.isNoneEmpty(sortBy)) {
            if (StringUtils.isNoneEmpty(order)) {
                orderBySQL = orderBySQL + sortBy + " " + order.toUpperCase() + "";
            } else {
                orderBySQL = orderBySQL + sortBy + " " + "ASC";
            }
        }
        if (orderBySQL.equals(" ORDER BY ")) {
            orderBySQL = "";
        }


        SqlSpecification specification = new CertificateAllSpecification(whereSQL, orderBySQL);
        List<Certificate> certificates = certificateRepository.query(specification);

        addTagsToListCertificates(certificates);
        return CertificateConverterDTO.convertToListDTO(certificates);
    }

    private List<Certificate> addTagsToListCertificates(List<Certificate> certificates) {
        List<Certificate> certificatesWithTags = new ArrayList<>();

        certificates.forEach(certificate -> {
            addTagsToCertificate(certificate);
            certificatesWithTags.add(certificate);
        });
        return certificatesWithTags;
    }

    private Certificate addTagsToCertificate(Certificate certificate) {
        Set<Tag> tags = tagRepository.getTagsByCertificateId(certificate.getId());
        certificate.setTags(tags);
        return certificate;
    }
}



