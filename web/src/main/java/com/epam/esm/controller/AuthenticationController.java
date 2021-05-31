//package com.epam.esm.controller;
//
//import com.epam.esm.dto.AuthenticationRequestDTO;
//import com.epam.esm.dto.UserDTO;
//import com.epam.esm.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api/v1/authentication")
//@AllArgsConstructor
//public class AuthenticationController {
//
//    private final UserService userService;
//
//    @PostMapping("/signup")
//    public UserDTO register(@RequestBody @Valid UserDTO userDTO) {
//        return userService.save(userDTO);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequestDTO requestDTO) {
//        return null;
//    }
//
//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response, null);
//    }
//}
