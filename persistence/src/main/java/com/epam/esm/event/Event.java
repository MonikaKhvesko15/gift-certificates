package com.epam.esm.event;

import com.epam.esm.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private OperationType operation;

    @Column(name = "date_time", updatable = false, columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "content_name", nullable = false, length = 255)
    private String contentName;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    public Event(OperationType operation, BaseEntity entity) {
        this.operation = operation;
        this.dateTime = LocalDateTime.now();
        this.contentName = entity.getClass().getSimpleName();
        this.contentId = entity.getId();
    }
}
