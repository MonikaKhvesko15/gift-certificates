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
import com.epam.esm.validator.TagDTOValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @Test
    void testGetByIdShouldReturnTagDTOWhenEntityExists() {
        Tag tag = new Tag((long) 1, "test");
        TagRepository repository = Mockito.mock(TagRepository.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        TagConverterDTO converterDTO = Mockito.mock(TagConverterDTO.class);
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.of(tag));
        TagServiceImpl service = new TagServiceImpl(repository, validator, converterDTO);
        TagDTO expected = new TagDTO((long) 1, "test");

        TagDTO actual = service.getById((long) 1);

        assertEquals(expected, actual);
    }

    @Test
    void testGetByIdShouldThrowExceptionWhenEntityNotFound() {
        TagRepository repository = Mockito.mock(TagRepository.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        TagConverterDTO converterDTO = Mockito.mock(TagConverterDTO.class);
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
        TagServiceImpl service = new TagServiceImpl(repository, validator, converterDTO);
        assertThrows(EntityNotFoundException.class, () -> service.getById((long) 1));
    }

    @Test
    void testGetAllShouldReturnListTagsDTO() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag((long) 1, "test1"));
        tags.add(new Tag((long) 2, "test2"));

        TagRepository repository = Mockito.mock(TagRepository.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        TagConverterDTO converterDTO = Mockito.mock(TagConverterDTO.class);
        Mockito.when(repository.query(Mockito.isA(TagAllSpecification.class))).thenReturn(tags);
        TagServiceImpl service = new TagServiceImpl(repository, validator, converterDTO);

        List<TagDTO> expected = new ArrayList<>();
        expected.add(new TagDTO((long) 1, "test1"));
        expected.add(new TagDTO((long) 2, "test2"));

        List<TagDTO> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreateShouldReturnTagDTOIfNameUnique() {
        Tag tag = new Tag((long) 1, "test");
        TagDTO expected = new TagDTO((long) 1, "test");
        TagRepositoryImpl repository = Mockito.mock(TagRepositoryImpl.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        TagConverterDTO converterDTO = Mockito.mock(TagConverterDTO.class);
        Mockito.when(validator.isValid(expected)).thenReturn(true);
        Mockito.when(repository.save(Mockito.isA(Tag.class))).thenReturn(tag);
        TagServiceImpl service = new TagServiceImpl(repository, validator, converterDTO);

        TagDTO actual = service.create(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateThrowsExceptionWhenNameNotUnique() {
        TagDTO tagDTO = new TagDTO((long) 1, "test");
        Optional<Tag> optionalTag = Optional.of(new Tag((long) 1, "test"));
        TagRepositoryImpl repository = Mockito.mock(TagRepositoryImpl.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        TagConverterDTO converterDTO = Mockito.mock(TagConverterDTO.class);
        Mockito.when(validator.isValid(tagDTO)).thenReturn(true);
        Mockito.when(repository.getByName(Mockito.anyString())).thenReturn(optionalTag);
        TagServiceImpl service = new TagServiceImpl(repository, validator, converterDTO);

        assertThrows(EntityAlreadyExistsException.class, () -> service.create(tagDTO));
    }

    @Test
    void testRemoveShouldReturnTrueWhenEntityDeleted() {
        TagRepository repository = Mockito.mock(TagRepository.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        TagConverterDTO converterDTO = Mockito.mock(TagConverterDTO.class);
        Mockito.when(repository.deleteById(Mockito.anyLong())).thenReturn(true);
        TagServiceImpl service = new TagServiceImpl(repository, validator, converterDTO);

        assertTrue(service.remove((long) 1));
    }

    @Test
    void testRemoveShouldReturnFalseWhenEntityNotDeleted() {
        TagRepository repository = Mockito.mock(TagRepository.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        TagConverterDTO converterDTO = Mockito.mock(TagConverterDTO.class);
        Mockito.when(repository.deleteById(Mockito.anyLong())).thenReturn(false);
        TagServiceImpl service = new TagServiceImpl(repository, validator, converterDTO);

        assertFalse(service.remove((long) 1));
    }
}
