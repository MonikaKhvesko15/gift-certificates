package com.epam.esm.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    public abstract void setDeleted(boolean flag);
}
