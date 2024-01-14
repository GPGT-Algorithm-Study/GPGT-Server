package com.randps.randomdefence.domain.comment.mock;

import com.randps.randomdefence.domain.comment.domain.Comment;
import com.randps.randomdefence.domain.comment.dto.CommentDto;
import com.randps.randomdefence.domain.comment.service.port.CommentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeCommentRepository implements CommentRepository {

    private final List<Comment> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public List<Comment> findAll() {
        return data;
    }

    @Override
    public List<Comment> findAllByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).collect(Collectors.toList());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null || comment.getId() == 0L) {
            autoIncreasingCount++;
            Comment newComment = Comment.builder().id(autoIncreasingCount).boardId(comment.getBoardId())
                    .bojHandle(comment.getBojHandle()).parentCommentId(comment.getParentCommentId())
                    .content(comment.getContent()).build();
            data.add(newComment);
            return newComment;
        } else {
            data.removeIf(item -> item.getId().equals(comment.getId()));
            Comment newComment = Comment.builder().id(comment.getId()).boardId(comment.getBoardId())
                    .bojHandle(comment.getBojHandle()).parentCommentId(comment.getParentCommentId())
                    .content(comment.getContent()).build();
            data.add(newComment);
            return newComment;
        }
    }

    @Override
    public void delete(Comment comment) {
        data.removeIf(item -> item.getId().equals(comment.getId()));
    }

    @Override
    public List<CommentDto> findAllByBoardId(Long boardId) {
        return data.stream().filter(item -> item.getBoardId().equals(boardId))
                .map(item -> CommentDto.builder().id(item.getId()).boardId(item.getBoardId())
                        .bojHandle(item.getBojHandle()).parentCommentId(item.getParentCommentId())
                        .content(item.getContent()).build()).collect(Collectors.toList());
    }

    @Override
    public List<Comment> findAllByParentCommentId(Long ParentCommentId) {
        return data.stream().filter(item -> item.getParentCommentId().equals(ParentCommentId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        data.removeIf(item -> item.getBojHandle().equals(bojHandle));
    }

    @Override
    public void deleteAllByBoardId(Long boardId) {
        data.removeIf(item -> item.getBoardId().equals(boardId));
    }
}
