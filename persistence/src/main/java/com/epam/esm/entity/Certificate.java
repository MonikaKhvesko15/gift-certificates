package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "gift_certificates")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "tags", callSuper = false)
@EntityListeners(AuditListener.class)
public class Certificate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @Column(name = "create_date", updatable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
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
