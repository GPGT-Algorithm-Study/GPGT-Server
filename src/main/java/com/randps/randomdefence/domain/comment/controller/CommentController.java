package com.randps.randomdefence.domain.comment.controller;

import com.randps.randomdefence.domain.comment.domain.Comment;
import com.randps.randomdefence.domain.comment.dto.CommentDto;
import com.randps.randomdefence.domain.comment.dto.CommentPublishRequest;
import com.randps.randomdefence.domain.comment.dto.CommentUpdateRequest;
import com.randps.randomdefence.domain.comment.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 특정 게시글의 댓글 생성
     */
    @PostMapping("/publish")
    public Comment publish(@RequestBody CommentPublishRequest commentPublishRequest) {
        return commentService.save(commentPublishRequest);
    }

    /**
     * 댓글Id로 특정 댓글 업데이트
     */
    @PutMapping("/update")
    public Comment update(@RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.update(commentUpdateRequest);
    }

    /**
     * 특정 댓글 삭제
     */
    @DeleteMapping("/del")
    public void deleteComment(@Param("commentId") Long commentId) {
        commentService.delete(commentId);
    }

    /**
     * 특정 게시글의 모든 댓글 조회
     */
    @GetMapping("/all")
    public List<CommentDto> findAllByBoardId(@Param("boardId") Long boardId) {
        return commentService.findAllByBoardId(boardId);
    }
}
