package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = "certificates", callSuper = true)
@Table(name = "orders")
public class Order extends BaseEntity {

    public static final String USER_ID_ATTRIBUTE = "user_id";

    @Column(name = "total_price", updatable = false)
    private BigDecimal totalPrice;

    @Column(name = "create_date", updatable = false, columnDefinition = "TIMESTAMP")
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
