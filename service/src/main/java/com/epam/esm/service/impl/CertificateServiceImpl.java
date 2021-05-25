package com.epam.esm.service.impl;

import com.epam.esm.converter.DTOConverter;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import com.epam.esm.dto.CertificateRequestFieldDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.TagRepositoryImpl;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.certificate.CertificateByParamsSpecification;
import com.epam.esm.util.PageRequestDTOHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl extends AbstractService<CertificateDTO, Certificate>
        implements CertificateService {
    private final Repository<Tag> tagRepository;

    public CertificateServiceImpl(DTOConverter<Certificate, CertificateDTO> converter,
                                  Repository<Certificate> repository,
                                  PageRequestDTOHandler parser,
                                  TagRepositoryImpl tagRepositoryImpl) {
        super(converter, repository, parser);
        this.tagRepository = tagRepositoryImpl;
    }

    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        String name = certificateDTO.getName();
        if (repository.getByName(name).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + name + ")");
        }
        Certificate certificate = converter.convertToEntity(certificateDTO);
        Set<Tag> tags = certificate.getTags();
        if (CollectionUtils.isNotEmpty(tags)) {
            createNotExistingTag(tags);
            certificate.setTags(getFullTags(certificate));
        }
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return converter.convertToDto(repository.save(certificate));
    }

    @Override
    public CertificateDTO update(Long id, CertificateDTO certificateDTO) {
        CertificateDTO formerCertificate = getById(id);
        String newName = certificateDTO.getName();
        if (repository.getByName(newName).isPresent()
                && !newName.equals(formerCertificate.getName())) {
            throw new EntityAlreadyExistsException(" (name = " + newName + ")");
        }
        Certificate certificate = converter.convertToEntity(certificateDTO);
        Set<Tag> updatedTags = certificate.getTags();
        if (CollectionUtils.isNotEmpty(updatedTags)) {
            createNotExistingTag(updatedTags);
            certificate.setTags(getFullTags(certificate));
        }
        certificate.setId(id);
        certificate.setCreateDate(formerCertificate.getCreateDate());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return converter.convertToDto(repository.update(certificate));
    }

    private Set<Tag> getFullTags(Certificate certificate) {
        return certificate.getTags().stream()
                .map(tag -> tagRepository.getByName(tag.getName())
                        .orElseThrow(() -> new EntityNotFoundException(" (tagId = " + tag.getId() + ")")))
                .collect(Collectors.toSet());
    }

    private void createNotExistingTag(Set<Tag> tags) {
        if (CollectionUtils.isNotEmpty(tags)) {
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
    public PageDTO<CertificateDTO> findByParams(CertificatePageQueryDTO queryDTO,
                                                PageRequestDTO pageRequestDTO) {
        PageRequestDTO pageParsed = pageHandler.checkPageRequest(pageRequestDTO);
        CriteriaSpecification<Certificate> specification = new CertificateByParamsSpecification(queryDTO.getTags(),
                queryDTO.getName(), queryDTO.getDescription(), queryDTO.getSortBy(), queryDTO.getOrder());
        List<Certificate> certificates = repository.getEntityListBySpecification(specification,
                Integer.parseInt(pageParsed.getPage().toString()),
                Integer.parseInt(pageParsed.getSize().toString()));
        List<CertificateDTO> certificateDTOList = converter.convertToListDTO(certificates);
        long totalElements = repository.countEntities(specification);
        return new PageDTO<>(
                Integer.parseInt(pageParsed.getPage().toString()),
                Integer.parseInt(pageParsed.getSize().toString()),
                totalElements,
                certificateDTOList);
    }

    @Override
    public CertificateDTO updateField(Long id, CertificateRequestFieldDTO field) {
        Certificate certificate = repository.getById(id)
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
        certificate.setLastUpdateDate(LocalDateTime.now());
        Certificate updatedCertificate = repository.save(certificate);
        return converter.convertToDto(updatedCertificate);
    }
}



