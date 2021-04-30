package com.epam.esm.metamodel;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Certificate.class)
public abstract class Certificate_ {
    public static volatile SingularAttribute<Certificate, Long> id;
    public static volatile SingularAttribute<Certificate, String> name;
    public static volatile SingularAttribute<Certificate, String> description;
    public static volatile SingularAttribute<Certificate, BigDecimal> price;
    public static volatile SingularAttribute<Certificate, Integer> duration;
    public static volatile SingularAttribute<Certificate, LocalDateTime> createDate;
    public static volatile SingularAttribute<Certificate, LocalDateTime> lastUpdateDate;
    public static volatile SingularAttribute<Certificate, Boolean> isDeleted;
    public static volatile SingularAttribute<Certificate, Tag> tags;

}
