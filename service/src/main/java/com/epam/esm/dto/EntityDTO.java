package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Setter
@Getter
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public abstract class EntityDTO extends RepresentationModel implements Serializable {
    private Long id;
}
