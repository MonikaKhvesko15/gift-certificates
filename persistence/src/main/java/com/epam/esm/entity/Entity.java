package com.epam.esm.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private Long id;

    protected Entity() {
    }

    protected Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
