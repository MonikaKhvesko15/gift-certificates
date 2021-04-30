package com.epam.esm.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {
    private Long id;

    protected BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
