package com.epam.esm.service.impl;

import com.epam.esm.converter.DTOConverter;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserRequestFieldDTO;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.UserService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.user.UserAllSpecification;
import com.epam.esm.util.PageRequestDTOHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractService<UserDTO, User> implements UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(DTOConverter<User, UserDTO> converter,
                           UserRepository repository,
                           PageRequestDTOHandler parser
//                           ,
//                           PasswordEncoder passwordEncoder
    ) {
        super(converter, repository, parser);
        this.userRepository = repository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getById(Long id) {
        User user = repository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(user);
    }

    @Override
    public PageDTO<UserDTO> findAll(PageRequestDTO pageRequestDTO) {
        PageRequestDTO pageParsed = pageHandler.checkPageRequest(pageRequestDTO);
        CriteriaSpecification<User> specification = new UserAllSpecification();
        List<User> userList = repository.getEntityListBySpecification(specification,
                Integer.parseInt(pageParsed.getPage().toString()),
                Integer.parseInt(pageParsed.getSize().toString()));
        List<UserDTO> userDTOList = converter.convertToListDTO(userList);
        long totalElements = repository.countEntities(specification);
        return new PageDTO<>(
                Integer.parseInt(pageParsed.getPage().toString()),
                Integer.parseInt(pageParsed.getSize().toString()),
                totalElements,
                userDTOList
        );
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        String username = userDTO.getEmail();
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException(" (username = " + username + ") ");
        }
        //todo:encode password
//        String password = userDTO.getPassword();
        User user = converter.convertToEntity(userDTO);
        user.setIsBlocked(false);
        user.setRole(UserRole.USER);
//        user.setPassword(passwordEncoder.encode(password));
        return converter.convertToDto(userRepository.save(user));
    }

    @Override
    public UserDTO update(Long id, UserRequestFieldDTO userRequestFieldDTO) {
        User user = repository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        if (StringUtils.isNotEmpty(userRequestFieldDTO.getEmail())) {
            user.setEmail(userRequestFieldDTO.getEmail());
        }
        if (StringUtils.isNotEmpty(userRequestFieldDTO.getFirstName())) {
            user.setFirstName(userRequestFieldDTO.getFirstName());
        }
        if (StringUtils.isNotEmpty(userRequestFieldDTO.getLastName())) {
            user.setLastName(userRequestFieldDTO.getLastName());
        }
        return converter.convertToDto(userRepository.update(user));

    }
}
