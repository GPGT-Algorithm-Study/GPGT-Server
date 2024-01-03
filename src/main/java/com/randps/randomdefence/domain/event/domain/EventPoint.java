package com.randps.randomdefence.domain.event.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "RD_EVENT_POINT")
@Entity
public class EventPoint extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double percentage;

    @Builder
    public EventPoint(Long id, String eventName, String description, LocalDateTime startTime, LocalDateTime endTime, Double percentage) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.percentage = percentage;
    }

    public EventPoint update(String eventName, String description, LocalDateTime startTime, LocalDateTime endTime, Double percentage) {
        this.eventName = eventName;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.percentage = percentage;

        return this;
    }

    Boolean isValid() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(startTime) && now.isBefore(endTime)) return true;
        else return false;
    }
}
