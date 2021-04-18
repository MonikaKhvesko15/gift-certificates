package com.epam.esm;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.CertificateConverterDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.validator.CertificateDTOValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CertificateServiceImplTest {
    @Test
    void testGetByIdShouldReturnCertificateDTOWhenEntityExists() {
        Certificate certificate = new Certificate.Builder(
                "test",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();
        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);

        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificate));
        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);
        CertificateDTO expected = converterDTO.convertToDto(certificate);

        CertificateDTO actual = service.getById((long) 1);

        assertEquals(expected, actual);
    }

    @Test
    void testGetByIdShouldThrowExceptionWhenEntityNotFound() {
        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);


        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);

        assertThrows(EntityNotFoundException.class, () -> service.getById((long) 1));
    }

    @Test
    void testCreateShouldReturnCertificateDTOIfNameUnique() {
        Certificate certificate = new Certificate.Builder(
                "test",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();

        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);
        CertificateDTO expected = converterDTO.convertToDto(certificate);

        Mockito.when(certificateDTOValidator.isValid(expected)).thenReturn(true);
        Mockito.when(certificateRepository.getByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(certificateRepository.save(certificate)).thenReturn(certificate);
        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificate));

        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);

        CertificateDTO actual = service.create(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateThrowsExceptionIfNameNotUnique() {
        Certificate certificate = new Certificate.Builder(
                "test",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();

        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);
        CertificateDTO expected = converterDTO.convertToDto(certificate);

        Mockito.when(certificateDTOValidator.isValid(expected)).thenReturn(true);
        Mockito.when(certificateRepository.getByName(Mockito.anyString())).thenReturn(Optional.of(certificate));
        Mockito.when(certificateRepository.save(certificate)).thenReturn(certificate);
        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificate));

        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);

        assertThrows(EntityAlreadyExistsException.class, () -> service.create(expected));
    }

    @Test
    void testUpdateShouldReturnCertificateDTOIfNameUnique() {
        Certificate certificate = new Certificate.Builder(
                "test",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();

        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);
        CertificateDTO expected = converterDTO.convertToDto(certificate);

        Mockito.when(certificateDTOValidator.isValid(expected)).thenReturn(true);
        Mockito.when(certificateRepository.getByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(certificateRepository.update(certificate)).thenReturn(certificate);
        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificate));

        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);

        CertificateDTO actual = service.update(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdateThrowsExceptionIfNameNotUnique() {
        Certificate certificate1 = new Certificate.Builder(
                "test1",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();
        Certificate certificate = new Certificate.Builder(
                "test",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();

        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);
        CertificateDTO expected = converterDTO.convertToDto(certificate);

        Mockito.when(certificateDTOValidator.isValid(expected)).thenReturn(true);
        Mockito.when(certificateRepository.getByName(Mockito.anyString())).thenReturn(Optional.of(certificate));
        Mockito.when(certificateRepository.update(certificate)).thenReturn(certificate);
        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificate1));

        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);

        assertThrows(EntityAlreadyExistsException.class, () -> service.update(expected));
    }

    @Test
    void testRemoveShouldReturnTrueWhenEntityDeleted() {
        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);

        Mockito.when(certificateRepository.deleteById(Mockito.anyLong())).thenReturn(true);
        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);


        assertTrue(service.remove((long) 1));
    }

    @Test
    void testRemoveShouldReturnFalseWhenEntityNotDeleted() {
        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);

        Mockito.when(certificateRepository.deleteById(Mockito.anyLong())).thenReturn(false);
        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);


        assertFalse(service.remove((long) 1));
    }

    @Test
    void testExecuteQueryDTOShouldReturnListCertificateDTOWhenRequestParamIsValid() {
        Certificate certificate1 = new Certificate.Builder(
                "test1",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();
        Certificate certificate2 = new Certificate.Builder(
                "test2",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();

        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate1);
        certificates.add(certificate2);
        CertificatePageQueryDTO queryDTO = new CertificatePageQueryDTO("test", "test", "name", "asc");
        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);

        Mockito.when(certificateRepository.query(Mockito.isA(CertificateAllSpecification.class))).thenReturn(certificates);
        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);
        List<CertificateDTO> expected = converterDTO.convertToListDTO(certificates);
        List<CertificateDTO> actual = service.executeQuery(queryDTO);

        assertEquals(expected, actual);
    }

    @Test
    void testExecuteQueryDTOShouldThrowsExceptionWhenRequestParamIsNotValid() {
        Certificate certificate1 = new Certificate.Builder(
                "test1",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();
        Certificate certificate2 = new Certificate.Builder(
                "test2",
                "test description",
                BigDecimal.valueOf(10),
                10)
                .id((long) 1).build();

        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate1);
        certificates.add(certificate2);
        CertificatePageQueryDTO queryDTO = new CertificatePageQueryDTO("test", "test", "name", "asc");
        CertificateRepository certificateRepository = Mockito.mock(CertificateRepository.class);
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        CertificateDTOValidator certificateDTOValidator = Mockito.mock(CertificateDTOValidator.class);
        CertificateConverterDTO converterDTO = Mockito.mock(CertificateConverterDTO.class);


        Mockito.when(certificateRepository.query(Mockito.isA(CertificateAllSpecification.class))).thenReturn(certificates);
        CertificateServiceImpl service = new CertificateServiceImpl(certificateRepository, tagRepository
                , certificateDTOValidator, converterDTO);

        assertThrows(Exception.class, () -> service.executeQuery(queryDTO));
    }
}
