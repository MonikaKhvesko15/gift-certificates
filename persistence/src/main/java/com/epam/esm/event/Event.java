package com.epam.esm.event;

import com.epam.esm.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation", nullable = false, updatable = false)
    private String operation;

    @Column(name = "date_time", updatable = false, columnDefinition = "TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime dateTime;

    @Column(name = "content_name", nullable = false, length = 255)
    private String contentName;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    public Event(String operation, LocalDateTime dateTime, String contentName, Long contentId) {
        this.operation = operation;
        this.dateTime = dateTime;
        this.contentName = contentName;
        this.contentId = contentId;
    }

    public static Event persistFor(BaseEntity entity) {
        return new Event(OperationType.PERSIST.getType(),
                LocalDateTime.now(), entity.getClass().getSimpleName(), entity.getId());
    }

    public static Event updateFor(BaseEntity entity) {
        return new Event(OperationType.UPDATE.getType(),
                LocalDateTime.now(), entity.getClass().getSimpleName(), entity.getId());
    }

    public static Event removeFor(BaseEntity entity) {
        return new Event(OperationType.REMOVE.getType(),
                LocalDateTime.now(), entity.getClass().getSimpleName(), entity.getId());
    }
}
