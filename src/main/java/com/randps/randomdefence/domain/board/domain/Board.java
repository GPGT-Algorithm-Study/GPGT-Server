package com.randps.randomdefence.domain.board.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String content;

    @Builder
    public Board(String type, String bojHandle, String title, String content) {
        this.type = type;
        this.bojHandle = bojHandle;
        this.title = title;
        this.content = content;
    }

    public void update(String type, String bojHandle, String title, String content) {
        this.type = type;
        this.bojHandle = bojHandle;
        this.title = title;
        this.content = content;
    }
}
