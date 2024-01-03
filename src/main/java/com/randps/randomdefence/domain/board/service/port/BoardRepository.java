package com.randps.randomdefence.domain.board.service.port;

import com.randps.randomdefence.domain.board.domain.Board;
import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import com.randps.randomdefence.domain.board.dto.SearchCondition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepository {

    Optional<Board> findById(Long id);

    List<Board> findAll();

    Board save(Board board);

    void delete(Board board);

    List<Board> findAllByBojHandle(String bojHandle);

    // 모든 게시글 페이징 조회
    Page<BoardSimple> findAllBoardSimplePaging(Pageable pageable);

    // 특정 type 게시글 페이징 조회
    Page<BoardSimple> findAllBoardSimpleByTypePaging(String type, Pageable pageable);

    // 특정 게시글 디테일 조회
    BoardDetail findBoardDetail(Long boardId);

    // 내가 쓴 글 페이징 조회
    Page<BoardSimple> findAllUserBoardSimplePaging(String bojHandle, Pageable pageable);

    // 제목으로 게시글 조회 (페이징)
    Page<BoardSimple> findAllBoardSimpleByQueryPaging(String query, Pageable pageable);

    // 게시글 조건 합쳐서 페이징 조회 (페이징)
    Page<BoardSimple> findAllBoardSimpleByConditionPaging(SearchCondition condition, Pageable pageable);

}
