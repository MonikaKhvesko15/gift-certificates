package com.epam.esm.impl;

import com.epam.esm.CertificateService;
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
import java.util.List;
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
        certificateWithTags(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        checkTags(certificate);
        certificate = certificateRepository.save(certificate);
        certificateWithTags(certificate);
        checkTags(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        certificate = certificateRepository.update(certificate);
        certificateRepository.deleteCertificateTags(certificate.getId());
        checkTags(certificate);
        certificateWithTags(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }


    private void checkTags(Certificate certificate) {
        Set<Tag> tags = certificate.getTags();
        if (!tags.isEmpty()) {
            tagRepository.createNewTags(tags);
            tagRepository.createCertificateTags(certificate);
        }
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }

    @Override
    public List<CertificateDTO> executeQueryDTO(CertificatePageQueryDTO queryDTO) {
        List<Certificate> certificates;
        SqlSpecification specification = new CertificateAllSpecification();

        String tagName = queryDTO.getTagName();
        if (StringUtils.isNoneEmpty(tagName)) {
            specification = new CertificatesByTagNameSpecification(tagName);
        }
        String context = queryDTO.getContext();
        if (StringUtils.isNoneEmpty(context)) {
            specification = new CertificateByPartOfContextSpecification(context);
        }
        String sortBy = queryDTO.getSortBy();
        String order = queryDTO.getOrder();
        if (StringUtils.isNoneEmpty(sortBy) && StringUtils.isNoneEmpty(order)) {
            specification = new SortCertificatesSpecification(sortBy, order);
        }

        certificates = certificateRepository.query(specification);
<<<<<<< HEAD
        certificates = certificateListWithTags(certificates);
=======
        certificates = certificatesWithTags(certificates);
>>>>>>> 14109588a72835d2e4eef70071d46783ca6e6c38

        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificates.forEach(certificate -> {
            CertificateDTO certificateDTO = CertificateConverterDTO.convertToDto(certificate);
            certificateDTOList.add(certificateDTO);
        });

        return certificateDTOList;
    }

<<<<<<< HEAD
    private List<Certificate> certificateListWithTags(List<Certificate> certificates) {
        List<Certificate> certificatesWithTags = new ArrayList<>();

        certificates.forEach(certificate -> {
            certificateWithTags(certificate);
=======
    private List<Certificate> certificatesWithTags(List<Certificate> certificates) {
        List<Certificate> certificatesWithTags = new ArrayList<>();

        certificates.forEach(certificate -> {
            certificate = certificateWithTags(certificate);
>>>>>>> 14109588a72835d2e4eef70071d46783ca6e6c38
            certificatesWithTags.add(certificate);
        });
        return certificatesWithTags;
    }

    private Certificate certificateWithTags(Certificate certificate) {
        Set<Tag> tags = tagRepository.getTagsByCertificateId(certificate.getId());
        certificate.setTags(tags);
        return certificate;
    }
}



