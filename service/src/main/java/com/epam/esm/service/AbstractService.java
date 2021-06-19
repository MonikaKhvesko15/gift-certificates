package com.epam.esm.service;

import com.epam.esm.converter.DTOConverter;
import com.epam.esm.dto.entityDTO.EntityDTO;
import com.epam.esm.entity.BaseEntity;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.util.PageRequestDTOHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractService<D extends EntityDTO, T extends BaseEntity> implements Service<D> {
    protected final DTOConverter<T, D> converter;
    protected final Repository<T> repository;
    protected PageRequestDTOHandler pageHandler;

    @Override
    public D getById(Long id) {
        T entity = repository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(entity);
    }

    @Override
    public void remove(Long id) {
        T entity = converter.convertToEntity(getById(id));
        repository.delete(entity);
    }
}
