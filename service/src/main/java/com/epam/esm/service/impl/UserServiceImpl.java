package com.epam.esm.service.impl;

import com.epam.esm.converter.UserDTOConverter;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.UserRepositoryImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.user.UserAllSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    Repository<User> userRepository;
    UserDTOConverter userDTOConverter;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository,
                           UserDTOConverter userDTOConverter) {
        this.userRepository = userRepository;
        this.userDTOConverter = userDTOConverter;
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return userDTOConverter.convertToDto(user);
    }

    @Override
    public List<UserDTO> findAll(PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<User> specification = new UserAllSpecification();
        List<User> userList = userRepository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getPage());
        return userDTOConverter.convertToListDTO(userList);
    }
}
