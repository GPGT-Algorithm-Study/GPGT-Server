package com.randps.randomdefence.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventPointPublishRequest {
    private String eventName;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double percentage;
}
