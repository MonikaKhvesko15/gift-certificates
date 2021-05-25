package com.epam.esm.converter;

import com.epam.esm.dto.EntityDTO;
import com.epam.esm.entity.BaseEntity;

import java.util.List;

public interface DTOConverter<T extends BaseEntity, D extends EntityDTO> {
    T convertToEntity(D entityDTO);

    D convertToDto(T entity);

    List<D> convertToListDTO(List<T> listEntity);

}
