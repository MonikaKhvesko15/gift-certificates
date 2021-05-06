package com.epam.esm.service;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;

public interface UserService {
    UserDTO getById(Long id);

    PageDTO<UserDTO> findAll(PageRequestDTO pageRequestDTO);
}
