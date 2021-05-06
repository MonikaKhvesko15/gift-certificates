package com.epam.esm.service.impl;

import com.epam.esm.converter.UserDTOConverter;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.UserRepositoryImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.service.util.PageDTOUtil;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.user.UserAllSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final Repository<User> userRepository;
    private final UserDTOConverter converter;
    private final PageDTOUtil<User, UserDTO> pageDTOUtil;

    public UserServiceImpl(UserRepositoryImpl userRepository,
                           UserDTOConverter userDTOConverter,
                           PageDTOUtil<User, UserDTO> pageDTOUtil) {
        this.userRepository = userRepository;
        this.converter = userDTOConverter;
        this.pageDTOUtil = pageDTOUtil;
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(user);
    }

    @Override
    public PageDTO<UserDTO> findAll(PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<User> specification = new UserAllSpecification();
        List<User> userList = userRepository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<UserDTO> userDTOList = converter.convertToListDTO(userList);
        return pageDTOUtil.fillPageDTO(userDTOList,
                pageRequestDTO, specification, userRepository);
    }
}
