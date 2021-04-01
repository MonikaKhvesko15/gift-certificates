package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/tags")
@RestController
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public List<Tag> findTags() {
        return tagService.getAll();
    }
}
