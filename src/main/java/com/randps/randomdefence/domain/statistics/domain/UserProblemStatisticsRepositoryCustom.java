package com.randps.randomdefence.domain.statistics.domain;

import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;

import java.util.List;

public interface UserProblemStatisticsRepositoryCustom {
    List<SolvedBarPair> findAllSolvedBarPair();
}
