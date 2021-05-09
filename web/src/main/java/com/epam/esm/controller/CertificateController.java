package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import com.epam.esm.dto.CertificateRequestFieldDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.PageDTOLinkBuilder;
import com.epam.esm.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The controller to provide CRUD operations on {@link CertificateDTO}.
 */
@RestController
@RequestMapping(value = "/v2/certificates")
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;
    private final LinkBuilder<CertificateDTO> certificateDTOLinkBuilder;
    private final PageDTOLinkBuilder<CertificateDTO> pageDTOLinkBuilder;

    /**
     * Search for certificates by passed params.
     *
     * @return the list of queried certificates or all certificates if no params passed
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<CertificateDTO> find(@Valid CertificatePageQueryDTO queryDTO,
                                        @Valid PageRequestDTO pageRequestDTO) {
        PageDTO<CertificateDTO> pageDTO = certificateService.findByParams(queryDTO, pageRequestDTO);
        pageDTO.getContent().forEach(certificateDTOLinkBuilder::toModel);
        pageDTOLinkBuilder.toModel(pageDTO);
        return pageDTO;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO updateDuration(@PathVariable Long id,
                                         @Valid CertificateRequestFieldDTO requestField) {
        return certificateService.updateField(id, requestField);
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
        certificateDTOLinkBuilder.toModel(certificateDTO);
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
        certificateDTOLinkBuilder.toModel(createdCertificate);
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
        certificateDTOLinkBuilder.toModel(updatedCertificate);
        return updatedCertificate;
    }
}
