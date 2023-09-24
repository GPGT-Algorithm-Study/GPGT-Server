package com.randps.randomdefence.domain.comment.domain;

import com.randps.randomdefence.domain.comment.dto.CommentDto;

import java.util.List;

public interface CommentRepositoryCustom {

    // 게시글 아이디로 모든 댓글 조회
    List<CommentDto> findAllByBoardId(Long boardId);

    // 부모 댓글 Id로 모든 댓글 조회
    List<Comment> findAllByParentCommentId(Long ParentCommentId);
}
