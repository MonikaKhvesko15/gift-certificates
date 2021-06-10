package com.epam.esm.dto.entityDTO;

import com.epam.esm.entity.UserRole;
import com.epam.esm.validator.EnumNamePattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends EntityDTO {
    @EnumNamePattern(regex = "USER|ADMIN")
    private UserRole roleName;
}
