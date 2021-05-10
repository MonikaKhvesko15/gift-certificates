package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NamedStoredProcedureQuery(
        name = "getMostWidelyUsedTag",
        procedureName = "fn_getMostWidelyUsedTag",
        resultClasses = {Tag.class},
        parameters = {
                @StoredProcedureParameter(name = "var_user_id", mode = ParameterMode.IN, type = Long.class)
        })
@EntityListeners(AuditListener.class)
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Override
    public void setDeleted(boolean flag) {
        throw new UnsupportedOperationException();
    }
}
