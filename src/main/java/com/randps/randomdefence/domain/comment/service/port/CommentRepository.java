package com.randps.randomdefence.domain.comment.service.port;

import com.randps.randomdefence.domain.comment.domain.Comment;
import com.randps.randomdefence.domain.comment.dto.CommentDto;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAll();

    List<Comment> findAllByBojHandle(String bojHandle);

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    void delete(Comment comment);

    // 게시글 아이디로 모든 댓글 조회
    List<CommentDto> findAllByBoardId(Long boardId);

    // 부모 댓글 Id로 모든 댓글 조회
    List<Comment> findAllByParentCommentId(Long ParentCommentId);

}
