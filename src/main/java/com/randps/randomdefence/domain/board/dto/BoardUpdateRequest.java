package com.randps.randomdefence.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequest {
    private Long boardId;

    private String type;

    private String bojHandle;

    private String title;

    private String content;

    private Integer problemId;

    private String imageUUIDs;
}
