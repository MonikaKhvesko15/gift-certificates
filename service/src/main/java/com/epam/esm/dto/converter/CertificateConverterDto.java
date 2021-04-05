package com.epam.esm.dto.converter;


import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import java.util.Set;
import java.util.stream.Collectors;


public class CertificateConverterDto {

    public static Certificate convertToEntity(CertificateDto certificateDto) {
        Set<Tag> tags = certificateDto.getTags().stream().map(TagConverterDto::convertToEntity).collect(Collectors.toSet());
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

    public static CertificateDto convertToDto(Certificate certificate) {
        Set<TagDto> tags = certificate.getTags().stream().map(TagConverterDto::convertToDto).collect(Collectors.toSet());
        return new CertificateDto(certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                tags);
    }
}
