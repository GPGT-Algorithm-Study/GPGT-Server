package com.randps.randomdefence.domain.comment.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.comment.domain.CommentRepositoryCustom;
import com.randps.randomdefence.domain.comment.dto.CommentDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.randps.randomdefence.domain.comment.domain.QComment.comment;
import static com.randps.randomdefence.domain.user.domain.QUser.user;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CommentDto> findAllByBoardId(Long boardId) {
        List<CommentDto> result = queryFactory
                .select(Projections.fields(
                        CommentDto.class,
                        comment.id,
                        comment.createdDate,
                        comment.modifiedDate,
                        comment.boardId,
                        comment.bojHandle,
                        user.emoji,
                        comment.parentCommentId,
                        comment.content
                ))
                .from(comment)
                .join(user).on(comment.bojHandle.eq(user.bojHandle))
                .where(comment.boardId.eq(boardId))
                .orderBy(comment.id.asc())
                .fetch();

        return result;
    }

    @Override
    public List<Comment> findAllByParentCommentId(Long parentCommentId) {
        List<Comment> result = queryFactory
                .selectFrom(comment)
                .where(comment.parentCommentId.eq(parentCommentId))
                .fetch();

        return result;
    }

}
