package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * The controller to provide CRUD operations on {@link CertificateDTO}.
 */
@RestController
@RequestMapping(value = "/v1/certificates")
@Validated
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Search for certificates by passed params.
     *
     * @param queryDTO Object containing all params
     * @return the list of queried certificates or all certificates if no params passed
     */
        @GetMapping()
        @ResponseStatus(HttpStatus.OK)
        public List<CertificateDTO> find(@RequestParam(required = false)
                                                 @Valid CertificatePageQueryDTO queryDTO) {
        if(queryDTO==null){
            queryDTO = new CertificatePageQueryDTO();
        }
        return certificateService.executeQuery(queryDTO);
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
        return certificateService.getById(id);
    }

    /**
     * Create certificate.
     *
     * @param certificateDTO The certificate to add
     * @return the {@link CertificateDTO} of added certificate and link to it
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO create(@RequestBody @Valid CertificateDTO certificateDTO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        CertificateDTO createdCertificateDTO = certificateService.create(certificateDTO);
        Long id = createdCertificateDTO.getId();
        String url = request.getRequestURL().toString();
        response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
        return createdCertificateDTO;
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
     * @param id  the id of certificate to update
     * @param certificateDTO the updated fields of certificate
     * @return the updated certificate {@link CertificateDTO}
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO update(@RequestBody @Valid CertificateDTO certificateDTO,
                                 @PathVariable Long id) {
        return certificateService.update(id, certificateDTO);
    }
}
