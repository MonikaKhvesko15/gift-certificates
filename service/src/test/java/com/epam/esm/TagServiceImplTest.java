package com.epam.esm;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.TagDTOValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagServiceImplTest {
    @Test
    public void getByIdShouldReturnTagDTOWhenEntityExists() {
        Tag tag = new Tag((long) 1, "test");
        TagRepository repository = Mockito.mock(TagRepository.class);
        TagDTOValidator validator = Mockito.mock(TagDTOValidator.class);
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.of(tag));
        TagServiceImpl service = new TagServiceImpl(repository, validator);
        TagDTO expected = new TagDTO((long) 1, "test");

        TagDTO actual = service.getById((long) 1);

        assertEquals(expected, actual);
    }
}
