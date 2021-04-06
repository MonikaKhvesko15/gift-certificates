package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateDTO> findAll() {
        return certificateService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO findById(@PathVariable Long id) {
        return certificateService.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO create(@RequestBody CertificateDTO certificateDto, HttpServletRequest request, HttpServletResponse response) {
        CertificateDTO certificateDTO1 = certificateService.create(certificateDto);
//        Long id = certificateDTO1.getId();
//        String url = request.getRequestURL().toString();
//        response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
        return certificateDTO1;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean delete(@PathVariable Long id) {
        return certificateService.remove(id);
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO update(@RequestBody CertificateDTO certificateDTO) {
        return certificateService.update(certificateDTO);
    }

}
