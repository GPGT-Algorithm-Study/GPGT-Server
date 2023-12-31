package com.randps.randomdefence.domain.statistics.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.statistics.dto.PointBarDto;
import com.randps.randomdefence.domain.statistics.dto.PointBarPair;
import com.randps.randomdefence.domain.statistics.dto.UserUserStatisticsPairDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.randps.randomdefence.domain.statistics.domain.QUserStatistics.userStatistics;
import static com.randps.randomdefence.domain.user.domain.QUser.user;

public class UserStatisticsRepositoryImpl implements UserStatisticsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserStatisticsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 팀번호로 팀 정보 조회
     */
    @Override
    public List<UserUserStatisticsPairDto> findAllByTeam(Integer team) {
        List<UserUserStatisticsPairDto> result = queryFactory
                .select(Projections.fields(
                        UserUserStatisticsPairDto.class,
                        user.bojHandle,
                        user.notionId,
                        user.profileImg,
                        user.emoji,
                        user.point,
                        userStatistics.weeklySolvedProblemCount,
                        userStatistics.weeklyEarningPoint
                ))
                .from(user)
                .join(userStatistics).on(user.bojHandle.eq(userStatistics.bojHandle))
                .where(user.team.eq(team))
                .fetch();

        return result;
    }

    /**
     * 모든 유저에 대해 유저의 정보와 유저의 포인트 통계를 조회
     */
    @Override
    public List<PointBarPair> findAllUserAndUserStat() {
        List<PointBarPair> result = queryFactory
                .select(Projections.fields(
                        PointBarPair.class,
                        user.notionId,
                        user.emoji,
                        userStatistics.dailyEarningPoint.coalesce(0).as("dailyEarningPoint"),
                        userStatistics.weeklyEarningPoint.coalesce(0).as("weeklyEarningPoint"),
                        userStatistics.totalEarningPoint.coalesce(0).as("totalEarningPoint")
                ))
                .from(user)
                .join(userStatistics).on(user.bojHandle.eq(userStatistics.bojHandle))
                .fetch();

        return result;
    }

}
