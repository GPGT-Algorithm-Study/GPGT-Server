package com.randps.randomdefence.domain.statistics.mock;

import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import com.randps.randomdefence.domain.statistics.service.port.UserProblemStatisticsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserProblemStatisticsRepository implements UserProblemStatisticsRepository {

    private final List<UserProblemStatistics> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Optional<UserProblemStatistics> findByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).findAny();
    }

    @Override
    public UserProblemStatistics save(UserProblemStatistics userProblemStatistics) {
        if (userProblemStatistics.getId() == null || userProblemStatistics.getId() == 0L) {
            autoIncreasingCount++;
            UserProblemStatistics newUserProblemStatistics = UserProblemStatistics.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(userProblemStatistics.getBojHandle())
                    .dailySolvedCountBronze(userProblemStatistics.getDailySolvedCountBronze())
                    .dailySolvedCountSilver(userProblemStatistics.getDailySolvedCountSilver())
                    .dailySolvedCountGold(userProblemStatistics.getDailySolvedCountGold())
                    .dailySolvedCountPlatinum(userProblemStatistics.getDailySolvedCountPlatinum())
                    .dailySolvedCountDiamond(userProblemStatistics.getDailySolvedCountDiamond())
                    .dailySolvedCountRuby(userProblemStatistics.getDailySolvedCountRuby())
                    .weeklySolvedCountBronze(userProblemStatistics.getWeeklySolvedCountBronze())
                    .weeklySolvedCountSilver(userProblemStatistics.getWeeklySolvedCountSilver())
                    .weeklySolvedCountGold(userProblemStatistics.getWeeklySolvedCountGold())
                    .weeklySolvedCountPlatinum(userProblemStatistics.getWeeklySolvedCountPlatinum())
                    .weeklySolvedCountDiamond(userProblemStatistics.getWeeklySolvedCountDiamond())
                    .weeklySolvedCountRuby(userProblemStatistics.getWeeklySolvedCountRuby())
                    .totalSolvedCountBronze(userProblemStatistics.getTotalSolvedCountBronze())
                    .totalSolvedCountSilver(userProblemStatistics.getTotalSolvedCountSilver())
                    .totalSolvedCountGold(userProblemStatistics.getTotalSolvedCountGold())
                    .totalSolvedCountPlatinum(userProblemStatistics.getTotalSolvedCountPlatinum())
                    .totalSolvedCountDiamond(userProblemStatistics.getTotalSolvedCountDiamond())
                    .totalSolvedCountRuby(userProblemStatistics.getTotalSolvedCountRuby())
                    .build();
            data.add(newUserProblemStatistics);
            return newUserProblemStatistics;
        } else {
            data.removeIf(item -> item.getId().equals(userProblemStatistics.getId()));
            UserProblemStatistics newUserProblemStatistics = UserProblemStatistics.builder()
                    .id(userProblemStatistics.getId())
                    .bojHandle(userProblemStatistics.getBojHandle())
                    .dailySolvedCountBronze(userProblemStatistics.getDailySolvedCountBronze())
                    .dailySolvedCountSilver(userProblemStatistics.getDailySolvedCountSilver())
                    .dailySolvedCountGold(userProblemStatistics.getDailySolvedCountGold())
                    .dailySolvedCountPlatinum(userProblemStatistics.getDailySolvedCountPlatinum())
                    .dailySolvedCountDiamond(userProblemStatistics.getDailySolvedCountDiamond())
                    .dailySolvedCountRuby(userProblemStatistics.getDailySolvedCountRuby())
                    .weeklySolvedCountBronze(userProblemStatistics.getWeeklySolvedCountBronze())
                    .weeklySolvedCountSilver(userProblemStatistics.getWeeklySolvedCountSilver())
                    .weeklySolvedCountGold(userProblemStatistics.getWeeklySolvedCountGold())
                    .weeklySolvedCountPlatinum(userProblemStatistics.getWeeklySolvedCountPlatinum())
                    .weeklySolvedCountDiamond(userProblemStatistics.getWeeklySolvedCountDiamond())
                    .weeklySolvedCountRuby(userProblemStatistics.getWeeklySolvedCountRuby())
                    .totalSolvedCountBronze(userProblemStatistics.getTotalSolvedCountBronze())
                    .totalSolvedCountSilver(userProblemStatistics.getTotalSolvedCountSilver())
                    .totalSolvedCountGold(userProblemStatistics.getTotalSolvedCountGold())
                    .totalSolvedCountPlatinum(userProblemStatistics.getTotalSolvedCountPlatinum())
                    .totalSolvedCountDiamond(userProblemStatistics.getTotalSolvedCountDiamond())
                    .totalSolvedCountRuby(userProblemStatistics.getTotalSolvedCountRuby())
                    .build();
            data.add(newUserProblemStatistics);
            return newUserProblemStatistics;
        }
    }

    @Override
    public List<UserProblemStatistics> findAll() {
        return data;
    }

    // TODO : implement
    @Override
    public List<SolvedBarPair> findAllSolvedBarPair() {
        return null;
    }
}
