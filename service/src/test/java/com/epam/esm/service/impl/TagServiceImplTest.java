package com.epam.esm.service.impl;

import com.epam.esm.converter.TagDTOConverter;
import com.epam.esm.dto.entityDTO.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class})
class TagServiceImplTest {
    @InjectMocks
    TagServiceImpl tagService;
    @Mock
    Repository<User> userRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    TagDTOConverter converter;


    Tag tagTestFirst = new Tag("test1");
    TagDTO tagDTOTest = new TagDTO("test");

    @Test
    void testGetByIdShouldReturnTagDTOWhenEntityExists() {
        Mockito.when(tagRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(tagTestFirst));
        Mockito.when(converter.convertToDto(tagTestFirst)).thenReturn(tagDTOTest);
        TagDTO actual = tagService.getById(1L);
        assertEquals(tagDTOTest, actual);
    }

    @Test
    void testGetByIdShouldThrowExceptionWhenEntityNotExists() {
        Mockito.when(tagRepository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> tagService.getById(1L));
    }

    @Test
    void testGetMostPopularTagShouldThrowExceptionWhenUserIdNotExists() {
        assertThrows(EntityNotFoundException.class, () -> tagService.getMostPopularTag(1L));
    }

    @Test
    void testCreateShouldReturnCreatedTagWhenTagNameNotExists() {
        Mockito.when(tagRepository.getByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(converter.convertToEntity(tagDTOTest)).thenReturn(tagTestFirst);
        Mockito.when(tagRepository.save(tagTestFirst)).thenReturn(tagTestFirst);
        Mockito.when(converter.convertToDto(tagTestFirst)).thenReturn(tagDTOTest);

        TagDTO actual = tagService.create(tagDTOTest);

        assertEquals(tagDTOTest, actual);
    }

    @Test
    void testCreateShouldThrowExceptionWhenTagNameExists() {
        Mockito.when(tagRepository.getByName(Mockito.anyString())).thenReturn(Optional.of(tagTestFirst));
        assertThrows(EntityAlreadyExistsException.class, () -> tagService.create(tagDTOTest));
    }
}
