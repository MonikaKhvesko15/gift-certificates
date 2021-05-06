package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CertificateDTOConverter {
    private final ModelMapper modelMapper;
    private final TagDTOConverter tagConverter;

    public CertificateDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.tagConverter = new TagDTOConverter(modelMapper);
    }

    public Certificate convertToEntity(CertificateDTO certificateDto) {
        Set<Tag> tags = new HashSet<>();
        if (!certificateDto.getTags().isEmpty()) {
            tags = certificateDto.getTags().stream()
                    .map(tagConverter::convertToEntity).collect(Collectors.toSet());
        }
        Certificate certificate = modelMapper.map(certificateDto, Certificate.class);
        certificate.setTags(tags);
        return certificate;
    }

    public CertificateDTO convertToDto(Certificate certificate) {
        Set<TagDTO> tags = certificate.getTags().stream()
                .map(tagConverter::convertToDto).collect(Collectors.toSet());
        CertificateDTO certificateDTO = modelMapper.map(certificate, CertificateDTO.class);
        certificateDTO.setTags(tags);
        return certificateDTO;
    }

    public List<CertificateDTO> convertToListDTO(List<Certificate> certificates) {
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificates.forEach(certificate -> {
            CertificateDTO certificateDTO = convertToDto(certificate);
            certificateDTOList.add(certificateDTO);
        });
        return certificateDTOList;
    }
}
