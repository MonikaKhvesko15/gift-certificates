package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class TagDTO implements Serializable {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;
}
