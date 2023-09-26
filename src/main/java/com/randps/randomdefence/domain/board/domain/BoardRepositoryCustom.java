package com.randps.randomdefence.domain.board.domain;

import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {

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
}