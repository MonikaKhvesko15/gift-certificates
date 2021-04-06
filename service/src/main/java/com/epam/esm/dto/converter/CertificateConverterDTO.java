package com.epam.esm.dto.converter;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;

import java.util.Set;
import java.util.stream.Collectors;


public class CertificateConverterDTO {

  //  private static final ModelMapper modelMapper = new ModelMapper();

    public static Certificate convertToEntity(CertificateDTO certificateDto) {
//        Certificate certificate = modelMapper.map(certificateDto, Certificate.class);
//        return certificate;

        Set<Tag> tags = certificateDto.getTags().stream().map(TagConverterDTO::convertToEntity).collect(Collectors.toSet());
        return new Certificate.Builder(
                certificateDto.getName(),
                certificateDto.getCreateDate(),
                certificateDto.getLastUpdateDate())
                .id(certificateDto.getId())
                .description(certificateDto.getDescription())
                .price(certificateDto.getPrice())
                .duration(certificateDto.getDuration())
                .tags(tags)
                .build();
    }

    public static CertificateDTO convertToDto(Certificate certificate) {
//        CertificateDTO certificateDTO = modelMapper.map(certificate, CertificateDTO.class);
//        return certificateDTO;
//
        Set<TagDTO> tags = certificate.getTags().stream().map(TagConverterDTO::convertToDto).collect(Collectors.toSet());
        return new CertificateDTO(certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                tags);
    }
}
