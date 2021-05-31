package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class TagDTO extends EntityDTO {

    public static final TagDTO empty = null;

    @NotBlank
    @Size(max = 50)
    private String name;

    public TagDTO(String name) {
        this.name = name;
    }

    public TagDTO(){
        super();
    }
}
