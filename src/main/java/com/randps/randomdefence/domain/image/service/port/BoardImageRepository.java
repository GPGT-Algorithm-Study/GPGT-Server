package com.randps.randomdefence.domain.image.service.port;

import com.randps.randomdefence.domain.image.domain.BoardImage;
import java.util.List;

public interface BoardImageRepository {

    List<BoardImage> findAllByBoardId(Long boardId);

    void deleteAllByBoardId(Long boardId);

    List<BoardImage> saveAll(List<BoardImage> boardImages);

    void delete(BoardImage boardImage);

}
