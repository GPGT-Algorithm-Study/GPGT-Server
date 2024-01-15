package com.randps.randomdefence.domain.image.infrastructure;

import com.randps.randomdefence.domain.image.domain.BoardImage;
import com.randps.randomdefence.domain.image.service.port.BoardImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardImageRepositoryAdapter implements BoardImageRepository {

    public final BoardImageJpaRepository boardImageJpaRepository;

    @Override
    public List<BoardImage> findAllByBoardId(Long boardId) {
        return boardImageJpaRepository.findAllByBoardId(boardId);
    }

    @Override
    public void deleteAllByBoardId(Long boardId) {
        boardImageJpaRepository.deleteAllByBoardId(boardId);
    }

    @Override
    public List<BoardImage> saveAll(List<BoardImage> boardImages) {
        return boardImageJpaRepository.saveAll(boardImages);
    }

    @Override
    public void delete(BoardImage boardImage) {
        boardImageJpaRepository.delete(boardImage);
    }

}
