package com.epam.esm.converter;

import com.epam.esm.dto.entityDTO.CertificateDTO;
import com.epam.esm.dto.entityDTO.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificateDTOConverter implements DTOConverter<Certificate, CertificateDTO>{
    private final ModelMapper modelMapper;
    private final TagDTOConverter tagConverter;

    @Override
    public Certificate convertToEntity(CertificateDTO certificateDto) {
        Set<Tag> tags = new HashSet<>();
        if (CollectionUtils.isNotEmpty(certificateDto.getTags())) {
            tags = certificateDto.getTags().stream()
                    .map(tagConverter::convertToEntity)
                    .collect(Collectors.toSet());
        }
        Certificate certificate = modelMapper.map(certificateDto, Certificate.class);
        certificate.setTags(tags);
        return certificate;
    }

    @Override
    public CertificateDTO convertToDto(Certificate certificate) {
        Set<TagDTO> tags = certificate.getTags().stream()
                .map(tagConverter::convertToDto)
                .collect(Collectors.toSet());
        CertificateDTO certificateDTO = modelMapper.map(certificate, CertificateDTO.class);
        certificateDTO.setTags(tags);
        return certificateDTO;
    }

    @Override
    public List<CertificateDTO> convertToListDTO(List<Certificate> certificates) {
        return certificates.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
