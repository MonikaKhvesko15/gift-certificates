package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(value = "/certificates")
@Validated
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateDTO> find(@Size(max = 50) @RequestParam(required = false, defaultValue = "") String tagName,
                                     @Size(max = 700) @RequestParam(required = false, defaultValue = "") String context,
                                     @RequestParam(required = false, defaultValue = "") String sortBy,
                                     @RequestParam(required = false, defaultValue = "") String order) {
        CertificatePageQueryDTO queryDTO = new CertificatePageQueryDTO(tagName, context, sortBy, order);
        return certificateService.executeQuery(queryDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO findById(@PathVariable Long id) {
        return certificateService.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO create(@RequestBody @Valid CertificateDTO certificateDto,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        CertificateDTO certificateDTO1 = certificateService.create(certificateDto);
        Long id = certificateDTO1.getId();
        String url = request.getRequestURL().toString();
        response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
        return certificateDTO1;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        certificateService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO update(@RequestBody @Valid CertificateDTO certificateDTO) {
        return certificateService.update(certificateDTO);
    }
}
