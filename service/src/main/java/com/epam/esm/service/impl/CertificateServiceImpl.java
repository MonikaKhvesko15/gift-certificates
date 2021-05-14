package com.epam.esm.service.impl;

import com.epam.esm.converter.CertificateDTOConverter;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import com.epam.esm.dto.CertificateRequestFieldDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepositoryImpl;
import com.epam.esm.repository.Repository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.certificate.CertificateByParamsSpecification;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final Repository<Certificate> certificateRepository;
    private final Repository<Tag> tagRepository;
    private final CertificateDTOConverter converter;

    @Override
    public CertificateDTO getById(Long id) {
        Certificate certificate = certificateRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(certificate);
    }

    @Override
    public void remove(Long id) {
        Certificate certificate = converter.convertToEntity(getById(id));
        certificateRepository.delete(certificate);
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
        CertificateDTO formerCertificate = getById(id);
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
        certificate.setCreateDate(formerCertificate.getCreateDate());
        return converter.convertToDto(certificateRepository.update(certificate));
    }


    @Override
    public PageDTO<CertificateDTO> findByParams(CertificatePageQueryDTO queryDTO, PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<Certificate> specification = new CertificateByParamsSpecification(queryDTO.getTags(),
                queryDTO.getName(), queryDTO.getDescription(), queryDTO.getSortBy(), queryDTO.getOrder());
        List<Certificate> certificates = certificateRepository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<CertificateDTO> certificateDTOList = converter.convertToListDTO(certificates);
        int totalElements = certificateRepository.countEntities(specification);
        return new PageDTO<>(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize(),
                totalElements,
                certificateDTOList);
    }

    @Override
    public CertificateDTO updateField(Long id, CertificateRequestFieldDTO field) {
        Certificate certificate = certificateRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        if (StringUtils.isNotEmpty(field.getName())) {
            certificate.setName(field.getName());
        }
        if (StringUtils.isNotEmpty(field.getDescription())) {
            certificate.setDescription(field.getDescription());
        }
        if (field.getPrice() != null) {
            certificate.setPrice(field.getPrice());
        }
        if (field.getDuration() != null) {
            certificate.setDuration(field.getDuration());
        }
        Certificate updatedCertificate = certificateRepository.save(certificate);
        return converter.convertToDto(updatedCertificate);
    }
}



