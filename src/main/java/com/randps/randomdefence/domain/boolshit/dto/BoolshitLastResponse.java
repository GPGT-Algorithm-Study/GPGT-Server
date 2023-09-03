package com.randps.randomdefence.domain.boolshit.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoolshitLastResponse {
    private Long id;

    private String message;

    private String notionId;

    private String emoji;

    private LocalDateTime writtenDate;

    @Builder
    public BoolshitLastResponse(Long id, String message, String notionId, String emoji, LocalDateTime writtenDate) {
        this.id = id;
        this.message = message;
        this.notionId = notionId;
        this.emoji = emoji;
        this.writtenDate = writtenDate;
    }

    public BoolshitLastResponse() {}
}
