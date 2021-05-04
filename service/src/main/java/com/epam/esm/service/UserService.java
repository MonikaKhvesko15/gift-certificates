package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO getById(Long id);

    Page<UserDTO> findAll(Pageable pageable);
}
