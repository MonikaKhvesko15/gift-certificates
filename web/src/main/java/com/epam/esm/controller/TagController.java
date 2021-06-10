package com.epam.esm.controller;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.entityDTO.TagDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.PageDTOLinkBuilder;
import com.epam.esm.service.TagService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

/**
 * The controller to provide CRD operations on {@link TagDTO}.
 */
@RestController
@RequestMapping(value = "/api/v1/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    private final LinkBuilder<TagDTO> tagDTOLinkBuilder;
    private final PageDTOLinkBuilder<TagDTO> pageDTOLinkBuilder;

    /**
     * Fids all tags.
     *
     * @return {@link PageDTO<TagDTO>} the list of all tags
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"admin","user"})
    public PageDTO<TagDTO> findAll(PageRequestDTO pageRequestDTO) {
        PageDTO<TagDTO> pageDTO = tagService.findAll(pageRequestDTO);
        if(CollectionUtils.isNotEmpty(pageDTO.getContent())) {
            pageDTO.getContent().forEach(tagDTOLinkBuilder::toModel);
            pageDTOLinkBuilder.toModel(pageDTO);
        }
        return pageDTO;
    }

    /**
     * Find tag by id.
     *
     * @param id the id of tag
     * @return the tag with queried id {@link TagDTO}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"admin","user"})
    public TagDTO findById(@PathVariable Long id) {
        TagDTO tagDTO = tagService.getById(id);
        tagDTOLinkBuilder.toModel(tagDTO);
        return tagDTO;
    }

    /**
     * Delete tag.
     *
     * @param id the id of tag to remove
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("admin")
    public void delete(@PathVariable Long id) {
        tagService.remove(id);
    }

    /**
     * Create tag.
     *
     * @param tagDTO the tag to add
     * @return the added tag {@link TagDTO} and link to it
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("admin")
    public TagDTO create(@RequestBody @Valid TagDTO tagDTO) {
        TagDTO createdTag = tagService.create(tagDTO);
        tagDTOLinkBuilder.toModel(createdTag);
        return createdTag;
    }
}
