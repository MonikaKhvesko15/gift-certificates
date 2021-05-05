package com.epam.esm.service;

import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getById(Long id);

    List<UserDTO> findAll(PageRequestDTO pageRequestDTO);
}
