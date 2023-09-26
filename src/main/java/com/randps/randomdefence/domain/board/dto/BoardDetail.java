package com.randps.randomdefence.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetail {
    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String type;

    private String bojHandle;

    private String notionId;

    private String emoji;

    private String title;

    private String content;

    private Long commentCount;
}
