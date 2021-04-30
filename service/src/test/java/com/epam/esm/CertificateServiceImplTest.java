package com.epam.esm;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class CertificateServiceImplTest {

    @InjectMocks
    CertificateServiceImpl certificateService;

//    @Mock
//    CertificateRepository certificateRepository;
//    @Mock
//    TagRepository tagRepository;
//    @Mock
//    CertificateConverterDTO converterDTO;
//
//    private Certificate certificate;
//    private Certificate certificate1;
//    private Certificate certificate2;
//    private CertificateDTO certificateDTO;
//
//    @BeforeEach
//    private void initTestParameters() {
//        certificate = new Certificate.Builder(
//                "test",
//                "test description",
//                BigDecimal.valueOf(10),
//                10)
//                .id(1L).build();
//
//        certificate1 = new Certificate.Builder(
//                "test1",
//                "test description1",
//                BigDecimal.valueOf(10),
//                10).id(2L).build();
//
//        certificate2 = new Certificate.Builder(
//                "test2",
//                "test description",
//                BigDecimal.valueOf(10),
//                10)
//                .id(3L).build();
//
//        certificateDTO = new CertificateDTO(
//                1L,
//                "test",
//                "test description",
//                BigDecimal.valueOf(10),
//                10
//        );
//    }
//
//    @Test
//    void testGetByIdShouldReturnCertificateDTOWhenEntityExists() {
//        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificate));
//        CertificateDTO expected = converterDTO.convertToDto(certificate);
//        CertificateDTO actual = certificateService.getById(1L);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void testGetByIdShouldThrowExceptionWhenEntityNotFound() {
//        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
//        assertThrows(EntityNotFoundException.class, () -> certificateService.getById(1L));
//    }
//
//    @Test
//    void testCreateShouldReturnCertificateDTOIfNameUnique() {
//        Mockito.when(certificateRepository.getByName(Mockito.anyString())).thenReturn(Optional.empty());
//        Mockito.when(converterDTO.convertToDto(certificate)).thenReturn(certificateDTO);
//        Mockito.when(converterDTO.convertToEntity(certificateDTO)).thenReturn(certificate);
//        Mockito.when(certificateRepository.save(certificate)).thenReturn(Optional.ofNullable(certificate));
//        CertificateDTO actual = certificateService.create(certificateDTO);
//        assertEquals(certificateDTO, actual);
//    }
//
//
//    @Test
//    void testUpdateShouldReturnCertificateDTOIfNameUnique() {
//        Mockito.when(certificateRepository.getByName(Mockito.anyString())).thenReturn(Optional.empty());
//        Mockito.when(certificateRepository.update(1L, certificate)).thenReturn(Optional.ofNullable(certificate));
//        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.of(certificate));
//        Mockito.when(converterDTO.convertToDto(certificate)).thenReturn(certificateDTO);
//        Mockito.when(converterDTO.convertToEntity(certificateDTO)).thenReturn(certificate);
//        CertificateDTO actual = certificateService.update(1L, certificateDTO);
//        assertEquals(certificateDTO, actual);
//    }
//
//    @Test
//    void testRemoveShouldThrowExceptionWhenEntityNotFound() {
//        Mockito.when(certificateRepository.getById(Mockito.anyLong())).thenReturn(Optional.empty());
//        assertThrows(EntityNotFoundException.class, () -> certificateService.remove(1L));
//    }
//
//    @Test
//    void testExecuteQueryDTOShouldReturnListCertificateDTOWhenRequestParamIsValid() {
//        List<Certificate> certificates = new ArrayList<>();
//        certificates.add(certificate1);
//        certificates.add(certificate2);
//        CertificatePageQueryDTO queryDTO = new CertificatePageQueryDTO("test", "test", "name", "asc");
//        Mockito.when(certificateRepository.query(Mockito.isA(CertificateAllSpecification.class))).thenReturn(certificates);
//        List<CertificateDTO> expected = converterDTO.convertToListDTO(certificates);
//        List<CertificateDTO> actual = certificateService.executeQuery(queryDTO);
//
//        assertEquals(expected, actual);
//    }
}
