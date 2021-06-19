package com.epam.esm.dto.entityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends EntityDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 15)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

//    @JsonProperty(access = Access.WRITE_ONLY)
    private Set<@Valid RoleDTO> roles;
    @Valid
    private Set<OrderDTO> orders;
}
