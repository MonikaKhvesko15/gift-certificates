package com.epam.esm.dto.converter;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class CertificateConverterDTO {

    public static Certificate convertToEntity(CertificateDTO certificateDto) {
        Set<Tag> tags = new HashSet<>();
        if(!certificateDto.getTags().isEmpty()){
            tags = certificateDto.getTags().stream().map(TagConverterDTO::convertToEntity).collect(Collectors.toSet());
        }
        return new Certificate.Builder(
                certificateDto.getName(),
                certificateDto.getDescription(),
                certificateDto.getPrice(),
                certificateDto.getDuration())
                .id(certificateDto.getId())
                .createDate(certificateDto.getCreateDate())
                .lastUpdateDate(certificateDto.getLastUpdateDate())
                .tags(tags)
                .build();
    }

    public static CertificateDTO convertToDto(Certificate certificate) {
        Set<TagDTO> tags = certificate.getTags().stream().map(TagConverterDTO::convertToDto).collect(Collectors.toSet());
        return new CertificateDTO(
                certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                tags);
    }
}
