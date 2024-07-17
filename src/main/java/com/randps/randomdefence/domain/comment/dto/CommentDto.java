package com.randps.randomdefence.domain.comment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String notionId;

    private String emoji;

    private String profileImg;

    private Long parentCommentId;

    private String content;
}
