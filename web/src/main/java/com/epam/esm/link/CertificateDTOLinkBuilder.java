package com.epam.esm.link;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CertificateDTOLinkBuilder implements LinkBuilder<CertificateDTO> {
    private final TagDTOLinkBuilder tagDTOLinkBuilder;

    @Override
    public void toModel(CertificateDTO certificateDTO) {
        if (certificateDTO.getTags() != null) {
            certificateDTO.getTags().forEach(tagDTOLinkBuilder::toModel);
        }
        certificateDTO.add(linkTo(CertificateController.class).withRel("certificates"));
        certificateDTO.add(linkTo(methodOn(CertificateController.class).findById(certificateDTO.getId())).withSelfRel());
    }
}
