package com.randps.randomdefence.domain.comment.dto;

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
public class CommentDto {

    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long boardId;

    private String bojHandle;

    private String emoji;

    private Long parentCommentId;

    private String content;
}
