package com.epam.esm.impl;

import com.epam.esm.CertificateQuery;
import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.CertificateConverterDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.specification.CertificateByPartOfDescriptionSpecification;
import com.epam.esm.specification.CertificateSpecificationByPartOfName;
import com.epam.esm.specification.CertificatesByTagNameSpecification;
import com.epam.esm.specification.SqlSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final Repository<Tag> tagRepository;


    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, Repository<Tag> tagRepository) {
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
        certificate = certificateRepository.save(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        certificate = certificateRepository.update(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    public List<CertificateDTO> findByQuery(CertificateQuery certificateQuery) {
        List<CertificateDTO> certificateDTOList = new ArrayList<>();

        if (!certificateQuery.getTagName().isEmpty()) {
            String tagName = certificateQuery.getTagName();
            SqlSpecification specification = new CertificatesByTagNameSpecification(tagName);
            List<Certificate> certificates = certificateRepository.query(specification);

            certificates.forEach(certificate -> {
                CertificateDTO certificateDTO = CertificateConverterDTO.convertToDto(certificate);
                certificateDTOList.add(certificateDTO);
            });
        }

        if (!certificateQuery.getName().isEmpty()) {
            String name = certificateQuery.getName();
            SqlSpecification specification = new CertificateSpecificationByPartOfName(name);

            List<Certificate> certificates = certificateRepository.query(specification);
            certificates.forEach(certificate -> {
                CertificateDTO certificateDTO = CertificateConverterDTO.convertToDto(certificate);
                certificateDTOList.add(certificateDTO);
            });
        }

        if (!certificateQuery.getDescription().isEmpty()) {
            String description = certificateQuery.getDescription();
            SqlSpecification specification = new CertificateByPartOfDescriptionSpecification(description);

            List<Certificate> certificates = certificateRepository.query(specification);
            certificates.forEach(certificate -> {
                CertificateDTO certificateDTO = CertificateConverterDTO.convertToDto(certificate);
                certificateDTOList.add(certificateDTO);
            });
        }

        if (!certificateQuery.getSortDate().isEmpty()) {
            certificateDTOList.stream().sorted().collect(Collectors.toList());
        }

        if (certificateQuery.getSortName().isEmpty()) {
            certificateDTOList.stream().sorted().collect(Collectors.toList());
        }

        return certificateDTOList;
    }

}
