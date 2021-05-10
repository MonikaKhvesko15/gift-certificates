package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@EqualsAndHashCode(exclude = "certificates", callSuper = false)
@RequiredArgsConstructor
@EntityListeners(AuditListener.class)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "total_price", updatable = false)
    private BigDecimal totalPrice;

    @Column(name = "create_date", updatable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "is_deleted", columnDefinition = "boolean default false", insertable = false)
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false)
    private OrderStatus status;

    @ManyToMany
    @JoinTable(name = "gift_certificates_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    private List<Certificate> certificates;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
