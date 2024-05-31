package com.randps.randomdefence.domain.event.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventPointUpdateRequest {
    private Long id;

    private String eventName;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double percentage;
}
