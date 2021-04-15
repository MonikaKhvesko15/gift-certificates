package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CertificateRepositoryTest {

    private static final Certificate TEST_CERTIFICATE = new Certificate.
            Builder("test", "certificate for test",
            BigDecimal.valueOf(10.0), 10)
            .id((long) 5)
            .build();

    private final CertificateRepositoryImpl certificateRepository;

    @Autowired
    public CertificateRepositoryTest(CertificateRepositoryImpl certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Test
    void testSaveShouldAddCertificateToDataSourceIfCertificateNotCreated() {
        Certificate expected = TEST_CERTIFICATE;

        Certificate actual = certificateRepository.save(expected);

        assertEquals(actual.getId(), expected.getId());
    }
}
