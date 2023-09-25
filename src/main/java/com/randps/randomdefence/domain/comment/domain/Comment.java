package com.randps.randomdefence.domain.comment.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_COMMENT")
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    private String bojHandle;

    private Long parentCommentId;

    private String content;

    @Builder
    public Comment(Long boardId, String bojHandle, Long parentCommentId, String content) {
        this.boardId = boardId;
        this.bojHandle = bojHandle;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public Comment update(Long boardId, String bojHandle, Long parentCommentId, String content) {
        this.boardId = boardId;
        this.bojHandle = bojHandle;
        this.parentCommentId = parentCommentId;
        this.content = content;

        return this;
    }
}
