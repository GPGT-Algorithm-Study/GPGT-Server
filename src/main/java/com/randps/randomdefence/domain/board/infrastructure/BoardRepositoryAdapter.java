package com.randps.randomdefence.domain.board.infrastructure;

import com.randps.randomdefence.domain.board.domain.Board;
import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import com.randps.randomdefence.domain.board.dto.SearchCondition;
import com.randps.randomdefence.domain.board.service.port.BoardRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryAdapter implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    private final BoardRepositoryImpl boardRepositoryImpl;

    @Override
    public Optional<Board> findById(Long id) {
        return boardJpaRepository.findById(id);
    }

    @Override
    public List<Board> findAll() {
        return boardJpaRepository.findAll();
    }

    @Override
    public Board save(Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public void delete(Board board) {
        boardJpaRepository.delete(board);
    }

    @Override
    public List<Board> findAllByBojHandle(String bojHandle) {
        return boardJpaRepository.findAllByBojHandle(bojHandle);
    }

    @Override
    public Page<BoardSimple> findAllBoardSimplePaging(Pageable pageable) {
        return boardRepositoryImpl.findAllBoardSimplePaging(pageable);
    }

    @Override
    public Page<BoardSimple> findAllBoardSimpleByTypePaging(String type, Pageable pageable) {
        return boardRepositoryImpl.findAllBoardSimpleByTypePaging(type, pageable);
    }

    @Override
    public BoardDetail findBoardDetail(Long boardId) {
        return boardRepositoryImpl.findBoardDetail(boardId);
    }

    @Override
    public Page<BoardSimple> findAllUserBoardSimplePaging(String bojHandle, Pageable pageable) {
        return boardRepositoryImpl.findAllUserBoardSimplePaging(bojHandle, pageable);
    }

    @Override
    public Page<BoardSimple> findAllBoardSimpleByQueryPaging(String query, Pageable pageable) {
        return boardRepositoryImpl.findAllBoardSimpleByQueryPaging(query, pageable);
    }

    @Override
    public Page<BoardSimple> findAllBoardSimpleByConditionPaging(SearchCondition condition, Pageable pageable) {
        return boardRepositoryImpl.findAllBoardSimpleByConditionPaging(condition, pageable);
    }
}
