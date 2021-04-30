package com.epam.esm;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class TagServiceImplTest {

//    @InjectMocks
//    TagServiceImpl tagService;
//
////    @Mock
////    TagRepository repository;
//    @Mock
//    TagConverterDTO converterDTO;
//
//    private Tag tag;
//    private TagDTO tagDTO;
//
//    @BeforeEach
//    private void initTestParameters() {
//        tag = new Tag(1L, "test");
//        tagDTO = new TagDTO(1L, "test");
//    }
//
//    @Test
//    void testGetByIdShouldReturnTagDTOWhenEntityExists() {
//        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.of(tag));
//        Mockito.when(converterDTO.convertToDto(tag)).thenReturn(tagDTO);
//        TagDTO actual = tagService.getById(1L);
//        assertEquals(tagDTO, actual);
//    }
//
//    @Test
//    void testGetByIdShouldThrowExceptionWhenEntityNotFound() {
//        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
//        assertThrows(EntityNotFoundException.class, () -> tagService.getById((long) 1));
//    }
//
//    @Test
//    void testCreateShouldReturnTagDTOIfNameUnique() {
//        Mockito.when(repository.save(Mockito.isA(Tag.class))).thenReturn(Optional.ofNullable(tag));
//        Mockito.when(converterDTO.convertToDto(tag)).thenReturn(tagDTO);
//        Mockito.when(converterDTO.convertToEntity(tagDTO)).thenReturn(tag);
//        TagDTO actual = tagService.create(tagDTO);
//        assertEquals(tagDTO, actual);
//    }
//
//
//    @Test
//    void testCreateThrowsExceptionWhenNameNotUnique() {
//        Mockito.when(repository.getByName(Mockito.anyString())).thenReturn(Optional.of(tag));
//        assertThrows(EntityAlreadyExistsException.class, () -> tagService.create(tagDTO));
//    }
//
//    @Test
//    void testRemoveShouldThrowExceptionWhenEntityNotFound() {
//        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
//        assertThrows(EntityNotFoundException.class, () -> tagService.remove(1L));
//    }
}
