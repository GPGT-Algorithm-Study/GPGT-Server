package com.randps.randomdefence.domain.comment.infrastructure;

import com.randps.randomdefence.domain.comment.domain.Comment;
import com.randps.randomdefence.domain.comment.dto.CommentDto;
import com.randps.randomdefence.domain.comment.service.port.CommentRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepositoryAdapter implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    private final CommentRepositoryImpl commentRepositoryImpl;

    @Override
    public List<Comment> findAll() {
        return commentJpaRepository.findAll();
    }

    @Override
    public List<Comment> findAllByBojHandle(String bojHandle) {
        return commentJpaRepository.findAllByBojHandle(bojHandle);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJpaRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentJpaRepository.delete(comment);
    }

    @Override
    public List<CommentDto> findAllByBoardId(Long boardId) {
        return commentRepositoryImpl.findAllByBoardId(boardId);
    }

    @Override
    public List<Comment> findAllByParentCommentId(Long ParentCommentId) {
        return commentRepositoryImpl.findAllByParentCommentId(ParentCommentId);
    }
}
