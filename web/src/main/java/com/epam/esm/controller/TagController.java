package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTO> findAll() {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO findById(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@RequestBody @Valid TagDTO tagDto, HttpServletRequest request, HttpServletResponse response) {
        TagDTO createdTagDTO = tagService.create(tagDto);
        Long id = createdTagDTO.getId();
        String url = request.getRequestURL().toString();
        response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
        return createdTagDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.remove(id);
    }
}
