package com.epam.esm.entity;

import com.epam.esm.event.EventListener;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(EventListener.class)
public abstract class BaseEntity implements Serializable {
    public static final String IS_DELETED_ATTRIBUTE = "isDeleted";
    public static final String ID_ATTRIBUTE = "id";
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public abstract void setDeleted(boolean flag);
}
