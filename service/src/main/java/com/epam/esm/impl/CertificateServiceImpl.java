package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.CertificateConverterDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.CertificateAllSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final Repository<Certificate> certificateRepository;
    private final Repository<Tag> tagRepository;


    @Autowired
    public CertificateServiceImpl(Repository<Certificate> certificateRepository, Repository<Tag> tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public List<CertificateDTO> getAll() {
        List<Certificate> certificates = certificateRepository.query(new CertificateAllSpecification());
        return certificates.stream().map(CertificateConverterDTO::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CertificateDTO getById(Long id) {
        Certificate certificate = certificateRepository.getById(id);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        Set<Tag> tags = certificate.getTags();
        if (!tags.isEmpty()) {
            saveNotExistTags(tags);
        }
        certificate = certificateRepository.save(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    private Set<Tag> saveNotExistTags(Set<Tag> tagSet) {
        Set<Tag> savedTags = new HashSet<>();
        tagSet.forEach(tag -> {
            if (tagRepository.getById(tag.getId()) == null) {
                savedTags.add(tagRepository.save(tag));
            } else {
                savedTags.add(tag);
            }
        });
        return savedTags;
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        Set<Tag> tags = certificate.getTags();
        if (!tags.isEmpty()) {
            tags = saveNotExistTags(tags);
        }
        certificate.setTags(tags);
        certificate = certificateRepository.update(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

}
