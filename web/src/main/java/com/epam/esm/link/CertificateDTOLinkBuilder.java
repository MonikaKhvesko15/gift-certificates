package com.epam.esm.link;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDTO;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateDTOLinkBuilder implements LinkBuilder<CertificateDTO> {

    @Override
    public void buildEntityLink(CertificateDTO certificateDTO) {
        certificateDTO.add(linkTo(CertificateController.class).withRel("certificates"));
        certificateDTO.add(linkTo(methodOn(CertificateController.class).findById(certificateDTO.getId())).withSelfRel());
    }
}
