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
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.specification.TagsByCertificateIdSpecification;
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
    private final CertificateConverterDTO converter;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  TagRepository tagRepository,
                                  CertificateConverterDTO converter) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.converter = converter;
    }

    @Override
    public CertificateDTO getById(Long id) {
        Certificate certificate = certificateRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        addTagsToCertificate(certificate);
        return converter.convertToDto(certificate);
    }


    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        String name = certificateDTO.getName();
        if (certificateRepository.getByName(name).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + name + ")");
        }

        Certificate certificate = converter.convertToEntity(certificateDTO);
        Set<Tag> tags = certificate.getTags();
        createNotExistingTag(tags);

        Certificate createdCertificate = certificateRepository.save(certificate);
        tagRepository.createCertificateTags(createdCertificate.getId(), tags);

        addTagsToCertificate(createdCertificate);
        return converter.convertToDto(createdCertificate);
    }

    @Override
    @Transactional
    public CertificateDTO update(Long id, CertificateDTO certificateDTO) {
        Certificate formerCertificate = certificateRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));

        String newName = certificateDTO.getName();
        if (certificateRepository.getByName(newName).isPresent()
                && !newName.equals(formerCertificate.getName())) {
            throw new EntityAlreadyExistsException(" (name = " + newName + ")");
        }

        Certificate certificateWithChanges = converter.convertToEntity(certificateDTO);
        certificateRepository.deleteCertificateTags(id);
        Set<Tag> updatedTags = certificateWithChanges.getTags();
        createNotExistingTag(updatedTags);
        tagRepository.createCertificateTags(id, updatedTags);

        Certificate updatedCertificate = certificateRepository.update(id, certificateWithChanges);
        addTagsToCertificate(updatedCertificate);
        return converter.convertToDto(updatedCertificate);
    }

    private void createNotExistingTag(Set<Tag> tags) {
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
        SqlSpecification specification = new CertificateAllSpecification(queryDTO.getTagName(),
                queryDTO.getContext(), queryDTO.getSortBy(), queryDTO.getOrder());
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



