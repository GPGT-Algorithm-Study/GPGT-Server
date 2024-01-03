package com.randps.randomdefence.domain.board.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Integer problemId;

    private Long commentCount;
}
