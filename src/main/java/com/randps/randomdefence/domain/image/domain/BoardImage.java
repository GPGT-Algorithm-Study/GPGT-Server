package com.randps.randomdefence.domain.image.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "RD_BOARD_IMAGE")
@Entity
public class BoardImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    private Long imageId;

    @Builder
    public BoardImage(Long boardId, Long imageId) {
        this.boardId = boardId;
        this.imageId = imageId;
    }
}
