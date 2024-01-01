package com.randps.randomdefence.domain.board.domain;

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
@Table(name = "RD_BOARD")
@Entity
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // '자유' , '문제풀이', '공지'

    private String bojHandle;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Integer problemId;

    @Builder
    public Board(Long id, String type, String bojHandle, String title, String content, Integer problemId) {
        this.id = id;
        this.type = type;
        this.bojHandle = bojHandle;
        this.title = title;
        this.content = content;
        this.problemId = problemId;
    }

    public void update(String type, String bojHandle, String title, String content, Integer problemId) {
        this.type = type;
        this.bojHandle = bojHandle;
        this.title = title;
        this.content = content;
        this.problemId = problemId;
    }
}
