package com.epam.esm.service.impl;

import com.epam.esm.converter.CertificateDTOConverter;
import com.epam.esm.dto.entityDTO.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
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
class CertificateServiceImplTest {
    @InjectMocks
    CertificateServiceImpl certificateService;
    @Mock
    Repository<Certificate> certificateRepository;
    @Mock
    CertificateDTOConverter converter;

    Certificate certificateTestFirst = new Certificate("certificate", "first", 50.5, 15);
    CertificateDTO certificateDTOTest = new CertificateDTO("certificate", "first", 12.3, 13);


    @Test
    void testGetByIdShouldReturnCertificateDTOWhenEntityExists() {
        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificateTestFirst));
        Mockito.when(converter.convertToDto(certificateTestFirst)).thenReturn(certificateDTOTest);
        CertificateDTO actual = certificateService.getById(1L);
        assertEquals(certificateDTOTest, actual);
    }

    @Test
    void testGetByIdShouldThrowExceptionWhenEntityNotExists() {
        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> certificateService.getById(1L));
    }

    @Test
    void testCreateShouldThrowExceptionWhenNameExists() {
        Mockito.when(certificateRepository.getByName(Mockito.anyString())).thenReturn(Optional.of(certificateTestFirst));

        assertThrows(EntityAlreadyExistsException.class, () -> certificateService.create(certificateDTOTest));
    }
}
