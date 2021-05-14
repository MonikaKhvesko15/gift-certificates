package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "gift_certificates")
public class Certificate extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @Column(name = "create_date", updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdateDate;

    @Column(name = "is_deleted", columnDefinition = "boolean default false", insertable = false)
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(name = "gift_certificates_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

}
