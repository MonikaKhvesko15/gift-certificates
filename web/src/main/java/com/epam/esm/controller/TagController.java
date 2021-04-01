package com.epam.esm.controller;

import com.epam.esm.TagService;

import com.epam.esm.entity.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public ResponseEntity<Tag> findTag() {
        tagService.getAll().forEach(System.out::println);
        System.out.println("*********");
        return ResponseEntity.ok(tagService.getTag());
    }
}
