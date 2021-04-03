package com.epam.esm;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface CertificateService {
    List<Certificate> getAll();

    Certificate getById(String id);

    Long create(Certificate certificate);

    boolean remove(Long id);
}
