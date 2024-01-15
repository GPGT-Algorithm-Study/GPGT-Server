package com.randps.randomdefence.domain.image.infrastructure;

import com.randps.randomdefence.domain.image.domain.BoardImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageJpaRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findAllByBoardId(Long boardId);

    void deleteAllByBoardId(Long boardId);
}
