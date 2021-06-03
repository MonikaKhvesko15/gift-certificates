package com.epam.esm.converter;

import com.epam.esm.dto.entityDTO.RoleDTO;
import com.epam.esm.entity.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleDTOConverter implements DTOConverter<Role, RoleDTO>{
    private final ModelMapper modelMapper;

    @Override
    public Role convertToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    @Override
    public RoleDTO convertToDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> convertToListDTO(List<Role> roles) {
        return roles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
