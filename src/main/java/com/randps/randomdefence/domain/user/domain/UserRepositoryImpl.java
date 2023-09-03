package com.randps.randomdefence.domain.user.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static com.randps.randomdefence.domain.user.domain.QUser.user;
import static com.randps.randomdefence.domain.user.domain.QUserRandomStreak.userRandomStreak;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
     * 모든 유저의 랜덤스트릭 정보를 포함해서 조회하고 반환한다.
     */
    @Override
    public List<UserInfoResponse> findAllUserResponse() {
        // 유저와 유저 랜덤 스트릭을 bojHandle로 조인해서 Tuple로 가져온다.
        List<UserInfoResponse> result = queryFactory
                .select(Projections.fields(
                        UserInfoResponse.class,
                        user.bojHandle,
                        user.notionId,
                        user.manager,
                        user.warning,
                        user.profileImg,
                        user.emoji,
                        user.tier,
                        user.totalSolved,
                        user.currentStreak,
                        user.currentRandomStreak,
                        user.team,
                        user.point,
                        user.isTodaySolved,
                        user.isTodayRandomSolved,
                        user.todaySolvedProblemCount,
                        userRandomStreak.maxRandomStreak
                        ))
                .from(user)
                .join(userRandomStreak).on(user.bojHandle.eq(userRandomStreak.bojHandle))
                .fetch();

        return result;
    }
}