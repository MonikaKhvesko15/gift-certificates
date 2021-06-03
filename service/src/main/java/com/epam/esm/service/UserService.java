package com.epam.esm.service;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.entityDTO.UserDTO;
import com.epam.esm.dto.UserRequestFieldDTO;

public interface UserService {
    PageDTO<UserDTO> findAll(PageRequestDTO pageRequestDTO);

    UserDTO register(UserDTO userDTO);

    UserDTO update(Long id, UserRequestFieldDTO userRequestFieldDTO);
}
