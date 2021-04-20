package com.epam.esm;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.TagConverterDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.TagRepositoryImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.specification.TagAllSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class TagServiceImplTest {

    @InjectMocks
    TagServiceImpl tagService;

    @Mock
    TagRepository repository;
    @Mock
    TagConverterDTO converterDTO;

    Tag tag = new Tag(1L, "test");
    TagDTO tagDTO = new TagDTO(1L, "test");

    @Test
    void testGetByIdShouldReturnTagDTOWhenEntityExists() {
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.of(tag));
        Mockito.when(converterDTO.convertToDto(tag)).thenReturn(tagDTO);
        TagDTO actual = tagService.getById(1L);
        assertEquals(tagDTO, actual);
    }

    @Test
    void testGetByIdShouldThrowExceptionWhenEntityNotFound() {
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> tagService.getById((long) 1));
    }

    @Test
    void testCreateShouldReturnTagDTOIfNameUnique() {
        Mockito.when(repository.save(Mockito.isA(Tag.class))).thenReturn(tag);
        Mockito.when(converterDTO.convertToDto(tag)).thenReturn(tagDTO);
        Mockito.when(converterDTO.convertToEntity(tagDTO)).thenReturn(tag);
        TagDTO actual = tagService.create(tagDTO);
        assertEquals(tagDTO, actual);
    }


    @Test
    void testCreateThrowsExceptionWhenNameNotUnique() {
        Mockito.when(repository.getByName(Mockito.anyString())).thenReturn(Optional.of(tag));
        assertThrows(EntityAlreadyExistsException.class, () -> tagService.create(tagDTO));
    }

    @Test
    void testRemoveShouldReturnTrueWhenEntityDeleted() {
        Mockito.when(repository.deleteById(Mockito.anyLong())).thenReturn(true);
        assertTrue(tagService.remove(1L));
    }

    @Test
    void testRemoveShouldReturnFalseWhenEntityNotDeleted() {
        Mockito.when(repository.deleteById(Mockito.anyLong())).thenReturn(false);
        assertFalse(tagService.remove(1L));
    }
}
