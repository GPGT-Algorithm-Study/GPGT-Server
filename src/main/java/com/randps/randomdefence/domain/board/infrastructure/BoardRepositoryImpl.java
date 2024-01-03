package com.randps.randomdefence.domain.board.infrastructure;

import static com.randps.randomdefence.domain.board.domain.QBoard.board;
import static com.randps.randomdefence.domain.comment.domain.QComment.comment;
import static com.randps.randomdefence.domain.user.domain.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import com.randps.randomdefence.domain.board.dto.SearchCondition;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
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
                        user.profileImg,
                        board.title,
                        board.content,
                        board.problemId,
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
                        user.profileImg,
                        board.title,
                        board.content,
                        board.problemId,
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
                        user.profileImg,
                        board.title,
                        board.content,
                        board.problemId,
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
                        user.profileImg,
                        board.title,
                        board.content,
                        board.problemId,
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
                        user.profileImg,
                        board.title,
                        board.content,
                        board.problemId,
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

    /**
     * 게시글 조회에서 사용하는 동적 쿼리를 위한 불리언 빌더
     */
    BooleanBuilder searchBuilder(SearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!condition.type.isEmpty() && !condition.type.isBlank())
            builder.and(board.type.eq(condition.type));

        if (!condition.bojHandle.isEmpty() && !condition.bojHandle.isBlank())
            builder.and(board.bojHandle.eq(condition.bojHandle));

        if (!condition.query.isEmpty() && !condition.query.isBlank())
            builder.and(board.title.contains(condition.query));

        return builder;
    }

    /**
     * 모든 조건에 따른 동적 페이징 조회
     */
    @Override
    public Page<BoardSimple> findAllBoardSimpleByConditionPaging(SearchCondition condition, Pageable pageable) {
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
                        user.profileImg,
                        board.title,
                        board.content,
                        board.problemId,
                        ExpressionUtils.as(
                                JPAExpressions.select(comment.count())
                                        .from(comment)
                                        .where(comment.boardId.eq(board.id)), "commentCount")
                ))
                .from(board)
                .join(user).on(user.bojHandle.eq(board.bojHandle))
                .where(searchBuilder(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.desc())
                .fetch();

        count = queryFactory
                .select(board.count())
                .from(board)
                .where(searchBuilder(condition))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

}
