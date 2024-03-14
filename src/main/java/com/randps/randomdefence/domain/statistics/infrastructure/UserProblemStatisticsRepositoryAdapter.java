package com.randps.randomdefence.domain.statistics.infrastructure;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import com.randps.randomdefence.domain.statistics.service.port.UserProblemStatisticsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserProblemStatisticsRepositoryAdapter implements UserProblemStatisticsRepository {

    private final UserProblemStatisticsJpaRepository userProblemStatisticsJpaRepository;

    private final UserProblemStatisticsRepositoryCustom userProblemStatisticsRepositoryCustom;

    @Override
    public Optional<UserProblemStatistics> findByBojHandle(String bojHandle) {
        return userProblemStatisticsJpaRepository.findByBojHandle(bojHandle);
    }

    @Override
    public UserProblemStatistics save(UserProblemStatistics userProblemStatistics) {
        return userProblemStatisticsJpaRepository.save(userProblemStatistics);
    }

    @Override
    public List<UserProblemStatistics> findAll() {
        return userProblemStatisticsJpaRepository.findAll();
    }

    @Override
    public List<SolvedBarPair> findAllSolvedBarPair() {
        return userProblemStatisticsRepositoryCustom.findAllSolvedBarPair();
    }
}
