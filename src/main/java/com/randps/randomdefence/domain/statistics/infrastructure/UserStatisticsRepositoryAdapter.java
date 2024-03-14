package com.randps.randomdefence.domain.statistics.infrastructure;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.dto.PointBarPair;
import com.randps.randomdefence.domain.statistics.dto.UserStatisticsPairDto;
import com.randps.randomdefence.domain.statistics.service.port.UserStatisticsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserStatisticsRepositoryAdapter implements UserStatisticsRepository {

    private final UserStatisticsJpaRepository userStatisticsJpaRepository;

    private final UserStatisticsRepositoryImpl userStatisticsRepository;

    @Override
    public Optional<UserStatistics> findByBojHandle(String bojHandle) {
        return userStatisticsJpaRepository.findByBojHandle(bojHandle);
    }

    @Override
    public UserStatistics save(UserStatistics userStatistics) {
        return userStatisticsJpaRepository.save(userStatistics);
    }

    @Override
    public List<UserStatistics> findAll() {
        return userStatisticsJpaRepository.findAll();
    }

    @Override
    public List<UserStatisticsPairDto> findAllByTeam(Integer team) {
        return userStatisticsRepository.findAllByTeam(team);
    }

    @Override
    public List<PointBarPair> findAllUserAndUserStat() {
        return userStatisticsRepository.findAllUserAndUserStat();
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        userStatisticsJpaRepository.deleteAllByBojHandle(bojHandle);
    }
}
