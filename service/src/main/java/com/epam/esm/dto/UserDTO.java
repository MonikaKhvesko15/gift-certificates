package com.epam.esm.dto;

import com.epam.esm.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends EntityDTO {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
    @NotBlank
    @Size(min = 10, max = 15)
    private String password;
    private UserRole role;
    @Valid
    private Set<OrderDTO> orders;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
