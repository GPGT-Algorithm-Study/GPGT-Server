package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserStatisticsRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserStatisticsService {

    private final UserRepository userRepository;

    private final UserStatisticsRepository userStatisticsRepository;

    private final UserProblemStatisticsRepository userProblemStatisticsRepository;

    private final UserProblemStatisticsService userProblemStatisticsService;

    private final ProblemService problemService;

    /*
     * 유저 통계를 생성한다. (초기화)
     */
    @Transactional
    public UserStatistics save(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 유저 난이도별 문제수 통계 생성
        userProblemStatisticsService.save(bojHandle);

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
    @Transactional
    public void updateById(String bojHandle, Integer problemId, Integer earningPoint) {
        UserStatistics userStatistics = userStatisticsRepository.findByBojHandle(bojHandle).orElse(save(bojHandle));
        ProblemDto problem = problemService.findProblem(problemId);

        // 유저 난이도별 문제수 통계 업데이트
        userProblemStatisticsService.updateByDto(bojHandle, problem);

        userStatistics.addStat(problem, earningPoint);
        userStatisticsRepository.save(userStatistics);
    }

    /*
     * 유저 통계 업데이트 (problemDto 이용)
     */
    @Transactional
    public void updateByDto(String bojHandle, ProblemDto problem, Integer earningPoint) {
        UserStatistics userStatistics = userStatisticsRepository.findByBojHandle(bojHandle).orElse(save(bojHandle));

        // 유저 난이도별 문제수 통계 업데이트
        userProblemStatisticsService.updateByDto(bojHandle, problem);

        userStatistics.addStat(problem, earningPoint);
        userStatisticsRepository.save(userStatistics);
    }

    /*
     * 유저 통계 조회
     */
    @Transactional
    public UserStatistics findByBojHandle(String bojHandle) {
        Optional<UserStatistics> stat = userStatisticsRepository.findByBojHandle(bojHandle);
        UserStatistics userStat;

        // 존재한다면 통계 반환
        if (stat.isPresent()) return stat.get();

        // 존재하지 않는다면 생성해서 저장 후 반환
        userStat = new UserStatistics(bojHandle);
        userStatisticsRepository.save(userStat);

        return userStat;
    }

    /*
     * 모든 유저 통계 일간 초기화
     */
    @Transactional
    public void initAllDailyStat() {
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findAll();
        List<UserProblemStatistics> userProblemStatistics = userProblemStatisticsRepository.findAll();

        // 푼 문제 난이도별 개수 초기화
        for (UserProblemStatistics userStat : userProblemStatistics) {
            userStat.initDaily();
            userProblemStatisticsRepository.save(userStat);
        }
        // 유저 통계 초기화
        for (UserStatistics userStat : userStatisticsList) {
            userStat.initDaily();
            userStatisticsRepository.save(userStat);
        }
    }

    /*
     * 모든 유저 통계 주간 초기화
     */
    @Transactional
    public void initAllWeeklyStat() {
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findAll();
        List<UserProblemStatistics> userProblemStatistics = userProblemStatisticsRepository.findAll();

        // 푼 문제 난이도별 개수 초기화
        for (UserProblemStatistics userStat : userProblemStatistics) {
            userStat.initDaily();
            userProblemStatisticsRepository.save(userStat);
        }
        // 유저 통계 초기화
        for (UserStatistics userStat : userStatisticsList) {
            userStat.initWeekly();
            userStatisticsRepository.save(userStat);
        }
    }

}
