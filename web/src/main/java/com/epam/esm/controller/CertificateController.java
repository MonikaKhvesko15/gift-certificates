package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.service.CertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * The controller to provide CRUD operations on {@link CertificateDTO}.
 */
@RestController
@RequestMapping(value = "/v1/certificates")
public class CertificateController {
    private final CertificateService certificateService;
    private final LinkBuilder<CertificateDTO> certificateDTOLinkBuilder;

    public CertificateController(CertificateService certificateService,
                                 LinkBuilder<CertificateDTO> certificateDTOLinkBuilder) {
        this.certificateService = certificateService;
        this.certificateDTOLinkBuilder = certificateDTOLinkBuilder;
    }

    /**
     * Search for certificates by passed params.
     *
     * @return the list of queried certificates or all certificates if no params passed
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateDTO> find(@Valid CertificatePageQueryDTO queryDTO) {
        List<CertificateDTO> certificateDTOList = certificateService.findByParams(queryDTO);
        certificateDTOList.forEach(certificateDTOLinkBuilder::buildEntityLink);
        return certificateDTOList;
    }

    /**
     * Find by id.
     *
     * @param id the id of certificate
     * @return the {@link CertificateDTO} of queried certificate
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO findById(@PathVariable Long id) {
        CertificateDTO certificateDTO = certificateService.getById(id);
        certificateDTOLinkBuilder.buildEntityLink(certificateDTO);
        return certificateDTO;
    }

    /**
     * Create certificateDTO.
     *
     * @param certificateDTO The certificateDTO to add
     * @return the {@link CertificateDTO} of added certificateDTO and link to it
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO create(@RequestBody @Valid CertificateDTO certificateDTO) {
        CertificateDTO createdCertificate = certificateService.create(certificateDTO);
        certificateDTOLinkBuilder.buildEntityLink(createdCertificate);
        return createdCertificate;
    }


    /**
     * Delete certificate.
     *
     * @param id the id of certificate
     * @return no content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        certificateService.remove(id);
    }


    /**
     * Update certificate.
     *
     * @param id             the id of certificate to update
     * @param certificateDTO the updated fields of certificate
     * @return the updated certificate {@link CertificateDTO}
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO update(@RequestBody @Valid CertificateDTO certificateDTO,
                                 @PathVariable Long id) {
        CertificateDTO updatedCertificate = certificateService.update(id, certificateDTO);
        certificateDTOLinkBuilder.buildEntityLink(updatedCertificate);
        return updatedCertificate;
    }
}
