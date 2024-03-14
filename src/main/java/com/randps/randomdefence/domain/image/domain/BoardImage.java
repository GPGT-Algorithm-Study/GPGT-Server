package com.randps.randomdefence.domain.image.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
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
@Table(name = "RD_BOARD_IMAGE")
@Entity
public class BoardImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    private Long imageId;

    @Builder
    public BoardImage(Long id, Long boardId, Long imageId) {
        this.id = id;
        this.boardId = boardId;
        this.imageId = imageId;
    }
}
