package com.randps.randomdefence.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSimple {
    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String type;

    private String bojHandle;

    private String notionId;

    private String emoji;

    private String profileImg;

    private String title;

    private String content;

    private Long commentCount;
}
