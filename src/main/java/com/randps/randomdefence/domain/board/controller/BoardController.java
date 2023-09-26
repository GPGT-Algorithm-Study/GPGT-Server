package com.randps.randomdefence.domain.board.controller;

import com.randps.randomdefence.domain.board.domain.Board;
import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardPublishRequest;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import com.randps.randomdefence.domain.board.dto.BoardUpdateRequest;
import com.randps.randomdefence.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {

    private final BoardService boardService;

    /**
     * 모든 게시글 조회 (테스트)
     */
    @GetMapping("/all/raw")
    public List<Board> findAll() {
        return boardService.findAll();
    }

    /**
     * 모든 게시글 페이징 조회
     */
    @GetMapping("/all")
    public Page<BoardSimple> findAllSimple(Pageable pageable) {
        return boardService.findAllSimple(pageable);
    }

    /**
     * type에 따른 게시글 페이징 조회
     */
    @GetMapping("/all/type")
    public Page<BoardSimple> findAllSimpleByType(@Param("type") String type, Pageable pageable) {
        return boardService.findAllSimpleByType(type, pageable);
    }

    /**
     * bojHandle에 따른 게시글 페이징 조회
     */
    @GetMapping("/all/user")
    public Page<BoardSimple> findAllSimpleByUser(@Param("bojHandle") String bojHandle, Pageable pageable) {
        return boardService.findAllSimpleByUser(bojHandle, pageable);
    }

    /**
     * qeury에 따른 게시글 제목으로 페이징 조회
     */
    @GetMapping("/all/query/title")
    public Page<BoardSimple> findAllSimpleByQuery(@Param("query") String query, Pageable pageable) {
        return boardService.findAllSimpleByQuery(query, pageable);
    }

    /**
     * 특정 게시글 디테일 조회
     */
    @GetMapping("/detail")
    public BoardDetail findDetail(@Param("boardId") Long boardId) {
        return boardService.findDetailByBoardId(boardId);
    }

    /**
     * 게시글 생성
     */
    @PostMapping("/publish")
    public Board publish(@RequestBody BoardPublishRequest boardPublishRequest) {
        return boardService.save(boardPublishRequest.getType()
                        ,boardPublishRequest.getBojHandle()
                        ,boardPublishRequest.getTitle()
                        ,boardPublishRequest.getContent()
                        ,boardPublishRequest.getImageUUIDs());
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/publish")
    public Board updatePublish(@RequestBody BoardUpdateRequest boardUpdateRequest) {
        return boardService.update(boardUpdateRequest.getBoardId()
                ,boardUpdateRequest.getType()
                ,boardUpdateRequest.getBojHandle()
                ,boardUpdateRequest.getTitle()
                ,boardUpdateRequest.getContent()
                ,boardUpdateRequest.getImageUUIDs());
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String,String>> delete(@Param("boardId") Long boardId) {
        boardService.delete(boardId);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "게시글을 성공적으로 삭제했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}
