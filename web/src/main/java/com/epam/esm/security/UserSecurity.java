package com.epam.esm.security;

import com.epam.esm.dto.entityDTO.UserDTO;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserSecurity {
    private final UserServiceImpl userService;

    public boolean hasSameName(Authentication authentication, Long id) {
        UserDTO userDTO = userService.getById(id);
        String existingUserLogin = userDTO.getEmail();
        String login = authentication.getName();
        return existingUserLogin.equals(login);
    }
}
