package com.epam.esm.service.impl;

import com.epam.esm.converter.DTOConverter;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.UserService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.user.UserAllSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractService<UserDTO, User> implements UserService {


    public UserServiceImpl(DTOConverter<User, UserDTO> converter,
                           Repository<User> repository) {
        super(converter, repository);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = repository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(user);
    }

    @Override
    public PageDTO<UserDTO> findAll(PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<User> specification = new UserAllSpecification();
        List<User> userList = repository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<UserDTO> userDTOList = converter.convertToListDTO(userList);
        long totalElements = repository.countEntities(specification);
        return new PageDTO<>(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize(),
                totalElements,
                userDTOList
        );
    }
}
