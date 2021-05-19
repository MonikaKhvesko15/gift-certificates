package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NamedStoredProcedureQuery(
        name = "getMostWidelyUsedTag",
        procedureName = "fn_getMostWidelyUsedTag",
        resultClasses = {Tag.class},
        parameters = {
                @StoredProcedureParameter(name = "var_user_id", mode = ParameterMode.IN, type = Long.class)
        })
@Table(name = "tags")
public class Tag extends BaseEntity {
    public static final String NAME_ATTRIBUTE = "name";

    @Column(unique = true)
    private String name;

    @Override
    public void setDeleted(boolean flag) {
        throw new UnsupportedOperationException();
    }

    public Tag(String name) {
        this.name = name;
    }
}
