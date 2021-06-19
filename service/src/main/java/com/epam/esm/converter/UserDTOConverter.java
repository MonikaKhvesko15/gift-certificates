package com.epam.esm.converter;

import com.epam.esm.dto.entityDTO.RoleDTO;
import com.epam.esm.dto.entityDTO.UserDTO;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDTOConverter implements DTOConverter<User, UserDTO> {
    private final ModelMapper modelMapper;
    private final RoleDTOConverter roleDTOConverter;

    @Override
    public User convertToEntity(UserDTO userDTO) {
        Set<Role> roles = new HashSet<>();
        if (CollectionUtils.isNotEmpty(userDTO.getRoles())) {
            roles = userDTO.getRoles().stream()
                    .map(roleDTOConverter::convertToEntity)
                    .collect(Collectors.toSet());
        }
        User user =  modelMapper.map(userDTO, User.class);
        user.setRoles(roles);
        return user;
    }

    @Override
    public UserDTO convertToDto(User user) {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        if(CollectionUtils.isNotEmpty(user.getRoles())){
            roleDTOS = user.getRoles().stream()
                    .map(roleDTOConverter::convertToDto)
                    .collect(Collectors.toSet());
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoles(roleDTOS);
        return userDTO;
    }

    @Override
    public List<UserDTO> convertToListDTO(List<User> users) {
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
