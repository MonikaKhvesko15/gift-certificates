package com.epam.esm.service;

import com.epam.esm.dto.entityDTO.EntityDTO;

public interface Service<D extends EntityDTO> {
    D getById(Long id);

    void remove(Long id);
}
