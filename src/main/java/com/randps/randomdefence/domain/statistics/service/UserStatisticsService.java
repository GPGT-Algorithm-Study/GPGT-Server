package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.problem.domain.Problem;
import com.randps.randomdefence.domain.problem.domain.ProblemRepository;
import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserStatisticsRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserStatisticsService {

    private final UserRepository userRepository;

    private final UserStatisticsRepository userStatisticsRepository;

    private final ProblemService problemService;

    /*
     * 유저 통계를 생성한다. (초기화)
     */
    public UserStatistics save(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return UserStatistics.builder()
                .bojHandle(bojHandle)
                .dailySolvedProblemCount(0)
                .dailySolvedMostDifficult(0)
                .dailySolvedMostDifficultProblemId(0)
                .dailyEarningPoint(0)
                .weeklySolvedProblemCount(0)
                .weeklySolvedMostDifficult(0)
                .weeklySolvedMostDifficultProblemId(0)
                .weeklyEarningPoint(0)
                .totalSolvedProblemCount(0)
                .totalSolvedMostDifficult(0)
                .totalSolvedMostDifficultProblemId(0)
                .totalEarningPoint(0)
                .build();
    }

    /*
     * 유저 통계 업데이트 (problemId 이용)
     */
    public void updateById(String bojHandle, Integer problemId, Integer earningPoint) {
        UserStatistics userStatistics = userStatisticsRepository.findByBojHandle(bojHandle).orElse(save(bojHandle));
        ProblemDto problem = problemService.findProblem(problemId);

        userStatistics.addStat(problem, earningPoint);
        userStatisticsRepository.save(userStatistics);
    }

    /*
     * 유저 통계 업데이트 (problemDto 이용)
     */
    public void updateByDto(String bojHandle, ProblemDto problem, Integer earningPoint) {
        UserStatistics userStatistics = userStatisticsRepository.findByBojHandle(bojHandle).orElse(save(bojHandle));

        userStatistics.addStat(problem, earningPoint);
        userStatisticsRepository.save(userStatistics);
    }

    /*
     * 유저 통계 조회
     */
    public UserStatistics findByBojHandle(String bojHandle) {
        return userStatisticsRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 통계입니다."));
    }

    /*
     * 모든 유저 통계 일간 초기화
     */
    public void initAllDailyStat() {
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findAll();

        for (UserStatistics userStat : userStatisticsList) {
            userStat.initDaily();
            userStatisticsRepository.save(userStat);
        }
    }

    /*
     * 모든 유저 통계 주간 초기화
     */
    public void initAllWeeklyStat() {
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findAll();

        for (UserStatistics userStat : userStatisticsList) {
            userStat.initWeekly();
            userStatisticsRepository.save(userStat);
        }
    }
}
