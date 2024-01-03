package com.randps.randomdefence.domain.statistics.infrastructure;

import static com.randps.randomdefence.domain.statistics.domain.QUserProblemStatistics.userProblemStatistics;
import static com.randps.randomdefence.domain.user.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserProblemStatisticsRepositoryImpl implements UserProblemStatisticsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserProblemStatisticsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 모든 유저의 Solved Bar통계 {User, UserProblemStatistics} DTO를 가져온다.
    @Override
    public List<SolvedBarPair> findAllSolvedBarPair() {
        List<SolvedBarPair> result = queryFactory
                .select(Projections.fields(
                        SolvedBarPair.class,
                        user.notionId,
                        user.emoji,
                        userProblemStatistics.dailySolvedCountBronze.coalesce(0).as("dailySolvedCountBronze"),
                        userProblemStatistics.dailySolvedCountSilver.coalesce(0).as("dailySolvedCountSilver"),
                        userProblemStatistics.dailySolvedCountGold.coalesce(0).as("dailySolvedCountGold"),
                        userProblemStatistics.dailySolvedCountPlatinum.coalesce(0).as("dailySolvedCountPlatinum"),
                        userProblemStatistics.dailySolvedCountDiamond.coalesce(0).as("dailySolvedCountDiamond"),
                        userProblemStatistics.dailySolvedCountRuby.coalesce(0).as("dailySolvedCountRuby"),
                        userProblemStatistics.weeklySolvedCountBronze.coalesce(0).as("weeklySolvedCountBronze"),
                        userProblemStatistics.weeklySolvedCountSilver.coalesce(0).as("weeklySolvedCountSilver"),
                        userProblemStatistics.weeklySolvedCountGold.coalesce(0).as("weeklySolvedCountGold"),
                        userProblemStatistics.weeklySolvedCountPlatinum.coalesce(0).as("weeklySolvedCountPlatinum"),
                        userProblemStatistics.weeklySolvedCountDiamond.coalesce(0).as("weeklySolvedCountDiamond"),
                        userProblemStatistics.weeklySolvedCountRuby.coalesce(0).as("weeklySolvedCountRuby"),
                        userProblemStatistics.totalSolvedCountBronze.coalesce(0).as("totalSolvedCountBronze"),
                        userProblemStatistics.totalSolvedCountSilver.coalesce(0).as("totalSolvedCountSilver"),
                        userProblemStatistics.totalSolvedCountGold.coalesce(0).as("totalSolvedCountGold"),
                        userProblemStatistics.totalSolvedCountPlatinum.coalesce(0).as("totalSolvedCountPlatinum"),
                        userProblemStatistics.totalSolvedCountDiamond.coalesce(0).as("totalSolvedCountDiamond"),
                        userProblemStatistics.totalSolvedCountRuby.coalesce(0).as("totalSolvedCountRuby")))
                .from(user)
                .join(userProblemStatistics).on(user.bojHandle.eq(userProblemStatistics.bojHandle))
                .fetch();

        return result;
    }
}
