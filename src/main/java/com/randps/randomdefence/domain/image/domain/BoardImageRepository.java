package com.randps.randomdefence.domain.image.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findAllByBoardId(Long boardId);

    void deleteAllByBoardId(Long boardId);
}
