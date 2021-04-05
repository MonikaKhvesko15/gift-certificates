package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.converter.CertificateConverterDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.specification.CertificateIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<CertificateDto> getAll() {
        List<Certificate> certificates = certificateRepository.queryForListResult(new CertificateAllSpecification());
        return certificates.stream().map(CertificateConverterDto::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CertificateDto getById(String id) {
        Optional<Certificate> optionalCertificate = certificateRepository.queryForSingleResult(new CertificateIdSpecification(id));
        Certificate certificate = optionalCertificate.orElseThrow(EntityNotFoundException::new);
        return CertificateConverterDto.convertToDto(certificate);
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        Certificate certificate = CertificateConverterDto.convertToEntity(certificateDto);
        certificate = certificateRepository.save(certificate);
        return CertificateConverterDto.convertToDto(certificate);
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }


}
