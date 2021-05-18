package com.epam.esm.service;

import com.epam.esm.dto.EntityDTO;

public interface Service<D extends EntityDTO> {
    D getById(Long id);
}
