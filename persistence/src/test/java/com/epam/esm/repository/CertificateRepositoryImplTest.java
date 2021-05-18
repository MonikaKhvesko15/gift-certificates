package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("dev")
@Transactional
class CertificateRepositoryImplTest {

    private static final long TEST_CERTIFICATE_ID = 5L;
    public static final long EXISTING_CERTIFICATE_ID = 4L;

    private static final String TEST_CERTIFICATE_NAME = "test";
    public static final String EXISTING_CERTIFICATE_NAME = "The fourth";

    private Certificate testCertificate;
    private Certificate existingCertificate;


    private final CertificateRepositoryImpl certificateRepositoryImpl;

    @Autowired
    public CertificateRepositoryImplTest(CertificateRepositoryImpl certificateRepositoryImpl) {
        this.certificateRepositoryImpl = certificateRepositoryImpl;
    }

    @BeforeEach
    private void initTestParameters() {

        testCertificate = new Certificate();

        existingCertificate = new Certificate();

    }


//    @Test
//    void testSaveShouldReturnCertificateWhenSaved() {
//        Certificate expected = testCertificate;
//        Optional<Certificate> actual = certificateRepository.save(expected);
//        assertTrue(actual.isPresent());
//    }
//
//    @Test
//    void testUpdateShouldReturnCertificateWhenUpdated() {
//        Certificate expected = existingCertificate;
//        Optional<Certificate> actual = certificateRepository.update(EXISTING_CERTIFICATE_ID, expected);
//        assertTrue(actual.isPresent());
//    }
//
//    @Test
//    void testDeleteByIdShouldReturnTrueWhenCertificateDeleted() {
//        boolean actual = certificateRepository.deleteById(EXISTING_CERTIFICATE_ID);
//        assertTrue(actual);
//    }
//
//    @Test
//    void testDeleteByIdShouldReturnFalseWhenCertificateNotDeleted() {
//        boolean actual = certificateRepository.deleteById(TEST_CERTIFICATE_ID);
//        assertFalse(actual);
//    }
//
//    @Test
//    void testGetByIdShouldReturnOptionalCertificateWhenFound() {
//        Optional<Certificate> certificateOptional = certificateRepository.getById(EXISTING_CERTIFICATE_ID);
//        assertEquals(certificateOptional, Optional.of(existingCertificate));
//    }
//
//    @Test
//    void testGetByIdShouldReturnOptionalEmptyWhenNotFound() {
//        Optional<Certificate> certificateOptional = certificateRepository.getById(TEST_CERTIFICATE_ID);
//        assertEquals(certificateOptional, Optional.empty());
//    }
//
//    @Test
//    void testGetByNameShouldReturnOptionalCertificateWhenFound() {
//        Optional<Certificate> certificateOptional = certificateRepository.getByName(EXISTING_CERTIFICATE_NAME);
//        assertEquals(certificateOptional, Optional.of(existingCertificate));
//    }
//
//    @Test
//    void testGetByNameShouldReturnOptionalEmptyWhenNotFound() {
//        Optional<Certificate> certificateOptional = certificateRepository.getByName(TEST_CERTIFICATE_NAME);
//        assertEquals(certificateOptional, Optional.empty());
//    }
}
