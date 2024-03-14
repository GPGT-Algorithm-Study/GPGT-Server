package com.randps.randomdefence.domain.statistics.service.port;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import java.util.List;
import java.util.Optional;

public interface UserProblemStatisticsRepository {

    Optional<UserProblemStatistics> findByBojHandle(String bojHandle);

    UserProblemStatistics save(UserProblemStatistics userProblemStatistics);

    List<UserProblemStatistics> findAll();

    List<SolvedBarPair> findAllSolvedBarPair();

}
