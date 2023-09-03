package com.randps.randomdefence.domain.boolshit.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.randps.randomdefence.domain.boolshit.domain.QBoolshit.boolshit;
import static com.randps.randomdefence.domain.user.domain.QUser.user;

public class BoolshitRepositoryImpl implements BoolshitRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    public BoolshitRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
     * 마지막 나의 한마디와 쓴 유저의 정보를 조회한다.
     */
    @Override
    public Optional<BoolshitLastResponse> findLastBoolshit() {
        BoolshitLastResponse boolshitLastResponse = queryFactory
                .select(Projections.fields(
                        BoolshitLastResponse.class,
                        boolshit.id,
                        boolshit.message,
                        user.notionId,
                        user.emoji,
                        boolshit.createdDate.as("writtenDate")
                ))
                .from(boolshit)
                .join(user).on(boolshit.bojHandle.eq(user.bojHandle))
                .orderBy(
                        boolshit.createdDate.desc()
                )
                .fetchFirst();

        return Optional.ofNullable(boolshitLastResponse);
    }

}
