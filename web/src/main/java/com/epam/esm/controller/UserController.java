package com.epam.esm.controller;

import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.UserDTOLinkBuilder;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v2/users")
public class UserController {
    private final UserService userService;
    private final LinkBuilder<UserDTO> linkBuilder;

    public UserController(UserServiceImpl userService,
                          UserDTOLinkBuilder linkBuilder) {
        this.userService = userService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable Long id) {
        UserDTO userDTO = userService.getById(id);
        linkBuilder.toModel(userDTO);
        return userDTO;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> findAll(@Valid PageRequestDTO pageRequestDTO) {
        List<UserDTO> userDTOList = userService.findAll(pageRequestDTO);
        userDTOList.forEach(linkBuilder::toModel);
        return userDTOList;
    }
}
