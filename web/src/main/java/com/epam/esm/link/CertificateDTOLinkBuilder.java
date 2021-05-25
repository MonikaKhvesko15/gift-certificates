package com.epam.esm.link;

import  com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CertificateDTOLinkBuilder implements LinkBuilder<CertificateDTO> {
    private static final Class<CertificateController> certificateControllerClass = CertificateController.class;
    private final TagDTOLinkBuilder tagDTOLinkBuilder;

    @Override
    public void toModel(CertificateDTO certificateDTO) {
        if (certificateDTO.getTags() != null) {
            certificateDTO.getTags().forEach(tagDTOLinkBuilder::toModel);
        }
        certificateDTO.add(linkTo(certificateControllerClass)
                .withRel("certificates"));
        certificateDTO.add(linkTo(methodOn(certificateControllerClass)
                .findById(certificateDTO.getId()))
                .withSelfRel());
        certificateDTO.add(linkTo(methodOn(certificateControllerClass)
                .update(certificateDTO, certificateDTO.getId()))
                .withRel("update"));
        certificateDTO.add(linkTo(certificateControllerClass)
                .slash(certificateDTO.getId())
                .withRel("delete"));
    }
}
