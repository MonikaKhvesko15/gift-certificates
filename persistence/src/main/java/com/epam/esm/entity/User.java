package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends BaseEntity {

    private String username;

    private String password;

    @Column(name = "is_deleted", columnDefinition = "boolean default false", insertable = false)
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private UserRole role;


}
