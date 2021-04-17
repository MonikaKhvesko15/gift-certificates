package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.CertificateConverterDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.util.CertificateParamsRequestUtil;
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.specification.TagsByCertificateIdSpecification;
import com.epam.esm.validator.CertificateDTOValidator;
import com.epam.esm.validator.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final DTOValidator<CertificateDTO> certificateDTOValidator;
    private final CertificateConverterDTO converter;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository,
                                  CertificateDTOValidator certificateDTOValidator,
                                  CertificateConverterDTO converter) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateDTOValidator = certificateDTOValidator;
        this.converter = converter;
    }

    @Override
    public CertificateDTO getById(Long id) {
        Certificate certificate = certificateRepository.getById(id).orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        addTagsToCertificate(certificate);
        return converter.convertToDto(certificate);
    }


    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        certificateDTOValidator.isValid(certificateDTO);
        String name = certificateDTO.getName();
        if (certificateRepository.getByName(name).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + name + ")");
        }
        Certificate certificate = converter.convertToEntity(certificateDTO);
        checkTags(certificate);
        certificate = certificateRepository.save(certificate);
        tagRepository.createCertificateTags(certificate);
        certificate = certificateRepository.getById(certificate.getId()).orElseThrow(EntityNotFoundException::new);
        addTagsToCertificate(certificate);
        return converter.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        certificateDTOValidator.isValid(certificateDTO);
        String name = certificateDTO.getName();
        if (certificateRepository.getByName(name).isPresent()
                && !name.equals(certificateRepository.getById(certificateDTO.getId()).get().getName())) {
            throw new EntityAlreadyExistsException(" (name = " + name + ")");
        }
        Certificate certificate = converter.convertToEntity(certificateDTO);
        certificateRepository.deleteCertificateTags(certificate.getId());
        checkTags(certificate);
        certificate = certificateRepository.update(certificate);
        tagRepository.createCertificateTags(certificate);
        certificate = certificateRepository.getById(certificate.getId()).orElseThrow(EntityNotFoundException::new);
        addTagsToCertificate(certificate);
        return converter.convertToDto(certificate);
    }

    private void checkTags(Certificate certificate) {
        Set<Tag> tags = certificate.getTags();
        if (!tags.isEmpty()) {
            Set<Tag> newTags = new HashSet<>();
            for (Tag tag : tags) {
                if (!tagRepository.getByName(tag.getName()).isPresent()) {
                    newTags.add(tag);
                }
            }
            newTags.forEach(tagRepository::save);
        }
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }

    @Override
    public List<CertificateDTO> executeQuery(CertificatePageQueryDTO queryDTO) {
        CertificateParamsRequestUtil paramsRequestUtil = new CertificateParamsRequestUtil(queryDTO);
        String whereSQL = paramsRequestUtil.getWhereQueryWithParams();
        String sortSQL = paramsRequestUtil.getSortQueryWithParams();
        SqlSpecification specification = new CertificateAllSpecification(whereSQL, sortSQL);
        List<Certificate> certificates = certificateRepository.query(specification);
        addTagsToListCertificates(certificates);
        return converter.convertToListDTO(certificates);
    }

    private void addTagsToListCertificates(List<Certificate> certificates) {
        certificates.forEach(this::addTagsToCertificate);
    }

    private void addTagsToCertificate(Certificate certificate) {
        SqlSpecification specification = new TagsByCertificateIdSpecification(certificate.getId());
        Set<Tag> tags = new HashSet<>(tagRepository.query(specification));
        certificate.setTags(tags);
    }
}



