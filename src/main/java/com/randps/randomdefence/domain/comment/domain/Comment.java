package com.randps.randomdefence.domain.comment.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Builder
    public Comment(Long id, Long boardId, String bojHandle, Long parentCommentId, String content) {
        this.id = id;
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
