package com.randps.randomdefence.domain.statistics.domain;

import static com.randps.randomdefence.domain.statistics.domain.QProblemStatistics.problemStatistics;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.randps.randomdefence.domain.statistics.dto.MostSolvedProblemDto;
import java.util.List;
import javax.persistence.EntityManager;

public class ProblemStatisticsRepositoryImpl implements ProblemStatisticsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProblemStatisticsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MostSolvedProblemDto> findAllMostSolvedProblems() {
        List<MostSolvedProblemDto> result = queryFactory
                .select(Projections.fields(
                        MostSolvedProblemDto.class,
                        problemStatistics.problemId.as("problemId"),
                        problemStatistics.solvedCount.as("solvedCount")))
                .from(problemStatistics)
                .orderBy(problemStatistics.solvedCount.desc())
                .limit(50)
                .fetch();

        return result;
    }
}
