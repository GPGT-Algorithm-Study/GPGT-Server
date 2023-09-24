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
}