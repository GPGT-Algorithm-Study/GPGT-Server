package com.randps.randomdefence.domain.statistics.infrastructure;

import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import java.util.List;

public interface UserProblemStatisticsRepositoryCustom {
    List<SolvedBarPair> findAllSolvedBarPair();
}
