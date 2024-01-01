package com.randps.randomdefence.domain.statistics.mock;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.dto.PointBarPair;
import com.randps.randomdefence.domain.statistics.dto.UserStatisticsPairDto;
import com.randps.randomdefence.domain.statistics.service.port.UserStatisticsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserStatisticsRepository implements UserStatisticsRepository {

    private final List<UserStatistics> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public Optional<UserStatistics> findByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).findAny();
    }

    @Override
    public UserStatistics save(UserStatistics userStatistics) {
        if (userStatistics.getId() == null || userStatistics.getId() == 0L) {
            autoIncreasingCount++;
            UserStatistics newUserStatistics = UserStatistics.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(userStatistics.getBojHandle())
                    .dailySolvedProblemCount(userStatistics.getDailySolvedProblemCount())
                    .dailySolvedMostDifficultProblemId(userStatistics.getDailySolvedMostDifficultProblemId())
                    .dailySolvedMostDifficult(userStatistics.getDailySolvedMostDifficult())
                    .dailyEarningPoint(userStatistics.getDailyEarningPoint())
                    .weeklySolvedProblemCount(userStatistics.getWeeklySolvedProblemCount())
                    .weeklySolvedMostDifficultProblemId(userStatistics.getWeeklySolvedMostDifficultProblemId())
                    .weeklySolvedMostDifficult(userStatistics.getWeeklySolvedMostDifficult())
                    .weeklyEarningPoint(userStatistics.getWeeklyEarningPoint())
                    .totalSolvedProblemCount(userStatistics.getTotalSolvedProblemCount())
                    .totalSolvedMostDifficultProblemId(userStatistics.getTotalSolvedMostDifficultProblemId())
                    .totalSolvedMostDifficult(userStatistics.getTotalSolvedMostDifficult())
                    .totalEarningPoint(userStatistics.getTotalEarningPoint())
                    .build();
            data.add(newUserStatistics);
            return newUserStatistics;
        } else {
            data.removeIf(item -> item.getId().equals(userStatistics.getId()));
            UserStatistics newUserStatistics = UserStatistics.builder()
                    .id(userStatistics.getId())
                    .bojHandle(userStatistics.getBojHandle())
                    .dailySolvedProblemCount(userStatistics.getDailySolvedProblemCount())
                    .dailySolvedMostDifficultProblemId(userStatistics.getDailySolvedMostDifficultProblemId())
                    .dailySolvedMostDifficult(userStatistics.getDailySolvedMostDifficult())
                    .dailyEarningPoint(userStatistics.getDailyEarningPoint())
                    .weeklySolvedProblemCount(userStatistics.getWeeklySolvedProblemCount())
                    .weeklySolvedMostDifficultProblemId(userStatistics.getWeeklySolvedMostDifficultProblemId())
                    .weeklySolvedMostDifficult(userStatistics.getWeeklySolvedMostDifficult())
                    .weeklyEarningPoint(userStatistics.getWeeklyEarningPoint())
                    .totalSolvedProblemCount(userStatistics.getTotalSolvedProblemCount())
                    .totalSolvedMostDifficultProblemId(userStatistics.getTotalSolvedMostDifficultProblemId())
                    .totalSolvedMostDifficult(userStatistics.getTotalSolvedMostDifficult())
                    .totalEarningPoint(userStatistics.getTotalEarningPoint())
                    .build();
            data.add(newUserStatistics);
            return newUserStatistics;
        }
    }

    @Override
    public List<UserStatistics> findAll() {
        return data;
    }

    // TODO : implement this method
    @Override
    public List<UserStatisticsPairDto> findAllByTeam(Integer team) {
        return null;
    }

    @Override
    public List<PointBarPair> findAllUserAndUserStat() {
        return null;
    }
}
