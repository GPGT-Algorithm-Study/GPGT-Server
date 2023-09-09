package com.randps.randomdefence.domain.user.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.statistics.dto.UserIsTodaySolvedDto;
import com.randps.randomdefence.domain.statistics.dto.UserWarningBarDto;
import com.randps.randomdefence.domain.statistics.dto.YesterdayUnsolvedUserDto;
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

    /*
     * 모든 유저의 경고 그래프 바 DTO를 조회한다.
     */
    @Override
    public List<UserWarningBarDto> findAllWarningBarDto() {
        List<UserWarningBarDto> result = queryFactory
                .select(Projections.fields(
                        UserWarningBarDto.class,
                        user.notionId,
                        user.emoji,
                        user.warning
                ))
                .from(user)
                .fetch();

        return result;
    }

    /*
     * 모든 유저의 오늘 문제 풀었는지 여부 DTO를 조회한다.
     */
    @Override
    public List<UserIsTodaySolvedDto> findAllUserIsTodaySolvedDto() {
        List<UserIsTodaySolvedDto> result = queryFactory
                .select(Projections.fields(
                        UserIsTodaySolvedDto.class,
                        user.notionId,
                        user.emoji,
                        user.isTodaySolved
                ))
                .from(user)
                .fetch();

        return result;
    }

    /*
     * 어제 문제를 풀지 않은 모든 유저를 DTO로 조회한다.
     */
    @Override
    public List<YesterdayUnsolvedUserDto> findAllYesterdayUnsolvedUserDto() {
        List<YesterdayUnsolvedUserDto> result = queryFactory
                .select(Projections.fields(
                        YesterdayUnsolvedUserDto.class,
                        user.bojHandle,
                        user.notionId,
                        user.profileImg,
                        user.emoji
                ))
                .from(user)
                .where(user.isYesterdaySolved.eq(false))
                .fetch();

        return result;
    }

}
