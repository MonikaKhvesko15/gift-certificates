package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationRequestDTO;
import com.epam.esm.dto.entityDTO.UserDTO;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/authentication")
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO register(@RequestBody @Valid UserDTO userDTO) {
        return userService.register(userDTO);
    }

//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.OK)
//    public Authentication login(@RequestBody @Valid AuthenticationRequestDTO requestDTO) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));
//        boolean isAuthenticated = isAuthenticated(authentication);
//        if (isAuthenticated) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        return authentication;
//    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}
