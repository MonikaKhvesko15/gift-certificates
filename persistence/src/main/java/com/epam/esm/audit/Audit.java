package com.epam.esm.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation", nullable = false, updatable = false)
    private String operation;

    @Column(name = "date_time", updatable = false, columnDefinition = "TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime dateTime;

    @Column(nullable = false, length = 10000)
    private String content;

    public Audit(String operation, LocalDateTime dateTime, String content) {
        this.operation = operation;
        this.dateTime = dateTime;
        this.content = content;
    }

    public static Audit persistFor(Object object) {
        return new Audit(OperationType.PERSIST.getType(), LocalDateTime.now(), object.toString());
    }

    public static Audit updateFor(Object object) {
        return new Audit(OperationType.UPDATE.getType(), LocalDateTime.now(), object.toString());
    }

    public static Audit removeFor(Object object) {
        return new Audit(OperationType.REMOVE.getType(), LocalDateTime.now(), object.toString());
    }
}
