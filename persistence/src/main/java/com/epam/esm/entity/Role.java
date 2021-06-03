package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends  BaseEntity{

    @Column(name = "name")
    private String name;

    @Override
    public void setDeleted(boolean flag) {
        throw new UnsupportedOperationException();
    }
}

