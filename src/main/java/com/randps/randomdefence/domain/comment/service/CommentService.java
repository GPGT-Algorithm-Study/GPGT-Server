package com.randps.randomdefence.domain.comment.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.randps.randomdefence.domain.comment.domain.Comment;
import com.randps.randomdefence.domain.comment.dto.CommentDto;
import com.randps.randomdefence.domain.comment.dto.CommentPublishRequest;
import com.randps.randomdefence.domain.comment.dto.CommentUpdateRequest;
import com.randps.randomdefence.domain.comment.service.port.CommentRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 코멘트 저장
     */
    @Transactional
    public Comment save(CommentPublishRequest commentPublishRequest) {
        Comment comment = Comment.builder().boardId(commentPublishRequest.getBoardId())
                .bojHandle(commentPublishRequest.getBojHandle())
                .parentCommentId(commentPublishRequest.getParentCommentId()).content(commentPublishRequest.getContent())
                .build();

        commentRepository.save(comment);

        return comment;
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public Comment update(CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentUpdateRequest.getCommentId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));

        // 수정
        comment.update(commentUpdateRequest.getBoardId(), commentUpdateRequest.getBojHandle(),
                commentUpdateRequest.getParentCommentId(), commentUpdateRequest.getContent());
        commentRepository.save(comment);

        return comment;
    }

    /**
     * 댓글Id로 댓글 삭제
     */
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));

        // 우선 삭제
        commentRepository.delete(comment);

        // 대댓글을 삭제하기 위해 모두 조회한다.
        List<Comment> subComments = commentRepository.findAllByParentCommentId(comment.getId());

        // 모든 서브 comment를 삭제했다면 종료
        if (subComments.size() == 0) {
            return;
        }

        // 재귀적 삭제
        for (Comment subComment : subComments) {
            delete(subComment.getId());
        }
    }

    /**
     * 게시글Id로 모든 댓글 조회
     */
    public List<CommentDto> findAllByBoardId(Long boardId) {
        return commentRepository.findAllByBoardId(boardId);
    }

    @Transactional
    public void deleteAllByBoardId(Long boardId) {
        commentRepository.deleteAllByBoardId(boardId);
    }

    @Transactional
    public void deleteAllByBojHandle(String bojHandle) {
        commentRepository.deleteAllByBojHandle(bojHandle);
    }
}
