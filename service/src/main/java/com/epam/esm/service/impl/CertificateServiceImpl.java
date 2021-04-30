package com.epam.esm.service.impl;

import com.epam.esm.converter.CertificateDTOConverter;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepositoryImpl;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.TagRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.certificate.CertificateByParamsSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepositoryImpl certificateRepository;
    private final Repository<Tag> tagRepository;
    private final CertificateDTOConverter converter;

    @Autowired
    public CertificateServiceImpl(CertificateRepositoryImpl certificateRepository,
                                  TagRepositoryImpl tagRepository,
                                  CertificateDTOConverter converter) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.converter = converter;
    }

    @Override
    public CertificateDTO getById(Long id) {
        //SqlSpecification specification = new CertificateByIdSpecification();
        Certificate certificate = certificateRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(certificate);
    }

    @Override
    public void remove(Long id) {
        if (!certificateRepository.getById(id).isPresent()) {
            throw new EntityNotFoundException(" (id = " + id + ")");
        }
        certificateRepository.deleteById(id);
    }

    @Override
    public List<CertificateDTO> findAll() {
        List<Certificate> certificates = certificateRepository.findAll();
        return converter.convertToListDTO(certificates);
    }

    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        String name = certificateDTO.getName();
        if (certificateRepository.getByName(name).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + name + ")");
        }

        Certificate certificate = converter.convertToEntity(certificateDTO);
        Set<Tag> tags = certificate.getTags();
        if (tags != null) {
            createNotExistingTag(tags);
        }

        return converter.convertToDto(certificateRepository.save(certificate));
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
    public CertificateDTO update(Long id, CertificateDTO certificateDTO) {
        Certificate formerCertificate = certificateRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));

        String newName = certificateDTO.getName();
        if (certificateRepository.getByName(newName).isPresent()
                && !newName.equals(formerCertificate.getName())) {
            throw new EntityAlreadyExistsException(" (name = " + newName + ")");
        }

        Certificate certificate = converter.convertToEntity(certificateDTO);
        Set<Tag> updatedTags = certificate.getTags();
        if (updatedTags != null) {
            createNotExistingTag(updatedTags);
        }
        certificate.setId(id);
        return converter.convertToDto(certificateRepository.update(certificate));
    }


    @Override
    public List<CertificateDTO> findByParams(CertificatePageQueryDTO queryDTO) {
        CriteriaSpecification<Certificate> specification = new CertificateByParamsSpecification(queryDTO.getTagName(),
                queryDTO.getName(), queryDTO.getDescription(), queryDTO.getSortBy(), queryDTO.getOrder());
        List<Certificate> certificates = certificateRepository.getEntityListBySpecification(specification);
        return converter.convertToListDTO(certificates);
    }

}



