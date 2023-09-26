package com.randps.randomdefence.domain.board.domain;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import com.randps.randomdefence.domain.image.domain.QBoardImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.randps.randomdefence.domain.board.domain.QBoard.board;
import static com.randps.randomdefence.domain.comment.domain.QComment.comment;
import static com.randps.randomdefence.domain.image.domain.QBoardImage.boardImage;
import static com.randps.randomdefence.domain.image.domain.QImage.image;
import static com.randps.randomdefence.domain.user.domain.QUser.user;
import static com.randps.randomdefence.domain.user.domain.QUserRandomStreak.userRandomStreak;

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
     * 모든 게시글 페이징 조회
     */
    @Override
    public Page<BoardSimple> findAllBoardSimplePaging(Pageable pageable) {
         List<BoardSimple> result = queryFactory
                .select(Projections.fields(
                        BoardSimple.class,
                        board.id,
                        board.createdDate,
                        board.modifiedDate,
                        board.type,
                        board.bojHandle,
                        user.notionId,
                        user.emoji,
                        board.title,
                        board.content,
                        ExpressionUtils.as(
                                JPAExpressions.select(comment.count())
                                        .from(comment)
                                        .where(comment.boardId.eq(board.id)), "commentCount")
                ))
                 .from(board)
                 .join(user).on(user.bojHandle.eq(board.bojHandle))
                 .offset(pageable.getOffset())
                 .limit(pageable.getPageSize())
                 .orderBy(board.createdDate.desc())
                 .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    /*
     * 특정 type 게시글 페이징 조회
     */
    @Override
    public Page<BoardSimple> findAllBoardSimpleByTypePaging(String type, Pageable pageable) {
        Long count;
        List<BoardSimple> result = queryFactory
                .select(Projections.fields(
                        BoardSimple.class,
                        board.id,
                        board.createdDate,
                        board.modifiedDate,
                        board.type,
                        board.bojHandle,
                        user.notionId,
                        user.emoji,
                        board.title,
                        board.content,
                        ExpressionUtils.as(
                                JPAExpressions.select(comment.count())
                                        .from(comment)
                                        .where(comment.boardId.eq(board.id)), "commentCount")
                ))
                .from(board)
                .join(user).on(user.bojHandle.eq(board.bojHandle))
                .where(board.type.eq(type))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.desc())
                .fetch();

        count = queryFactory
                .select(board.count())
                .from(board)
                .where(board.type.eq(type))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    /*
     * 특정 게시글 디테일 조회
     */
    @Override
    public BoardDetail findBoardDetail(Long boardId) {

        BoardDetail result = queryFactory
                .select(Projections.fields(
                        BoardDetail.class,
                        board.id,
                        board.createdDate,
                        board.modifiedDate,
                        board.type,
                        board.bojHandle,
                        user.notionId,
                        user.emoji,
                        board.title,
                        board.content,
                        ExpressionUtils.as(
                                JPAExpressions.select(comment.count())
                                        .from(comment)
                                        .where(comment.boardId.eq(board.id)), "commentCount")
                ))
                .from(board)
                .join(user).on(board.bojHandle.eq(user.bojHandle))
                .where(board.id.eq(boardId))
                .fetchOne();

        return result;
    }

    /**
     * 특정 유저의 아이디로 모든 글 페이징 조회
     */
    @Override
    public Page<BoardSimple> findAllUserBoardSimplePaging(String bojHandle, Pageable pageable) {
        Long count;
        List<BoardSimple> result = queryFactory
                .select(Projections.fields(
                        BoardSimple.class,
                        board.id,
                        board.createdDate,
                        board.modifiedDate,
                        board.type,
                        board.bojHandle,
                        user.notionId,
                        user.emoji,
                        board.title,
                        board.content,
                        ExpressionUtils.as(
                                JPAExpressions.select(comment.count())
                                        .from(comment)
                                        .where(comment.boardId.eq(board.id)), "commentCount")
                ))
                .from(board)
                .join(user).on(user.bojHandle.eq(board.bojHandle))
                .where(board.bojHandle.eq(bojHandle))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.desc())
                .fetch();

        count = queryFactory
                .select(board.count())
                .from(board)
                .where(board.bojHandle.eq(bojHandle))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    /**
     * 유저의 질의에 따른 like-query 조회
     */
    @Override
    public Page<BoardSimple> findAllBoardSimpleByQueryPaging(String query, Pageable pageable) {
        Long count;
        List<BoardSimple> result = queryFactory
                .select(Projections.fields(
                        BoardSimple.class,
                        board.id,
                        board.createdDate,
                        board.modifiedDate,
                        board.type,
                        board.bojHandle,
                        user.notionId,
                        user.emoji,
                        board.title,
                        board.content,
                        ExpressionUtils.as(
                                JPAExpressions.select(comment.count())
                                        .from(comment)
                                        .where(comment.boardId.eq(board.id)), "commentCount")
                ))
                .from(board)
                .join(user).on(user.bojHandle.eq(board.bojHandle))
                .where(board.title.contains(query))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.desc())
                .fetch();

        count = queryFactory
                .select(board.count())
                .from(board)
                .where(board.title.contains(query))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }
}
