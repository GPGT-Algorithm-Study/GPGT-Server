package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.statistics.domain.UserProblemStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.domain.UserStatisticsRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import com.randps.randomdefence.domain.user.service.UserSolvedProblemService;
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

    private final UserSolvedProblemService userSolvedProblemService;

    private final UserRandomStreakService userRandomStreakService;

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
            userStat.initWeekly();
            userProblemStatisticsRepository.save(userStat);
        }
        // 유저 통계 초기화
        for (UserStatistics userStat : userStatisticsList) {
            userStat.initDaily();
            userStat.initWeekly();
            userStatisticsRepository.save(userStat);
        }
    }

    /*
     * 모든 유저의 일일 문제풀이 통계를 신뢰성 있게 처음부터 다시 만든다.(버그가 발생 했을 시 데이터 정상화 메서드)
     */
    @Transactional
    public void integrityCheckTodayStatistics() {

        // 1.유저가 오늘 푼 문제를 계산한다.
        List<UserProblemStatistics> userProblemStatistics = userProblemStatisticsRepository.findAll();

        // 각각 유저의 정보를 가져와서 통계를 만들고 저장한다.
        for (UserProblemStatistics stat: userProblemStatistics) {
            // 유저가 오늘 푼 문제 통계 초기화
            stat.initDaily();

            // 오늘 푼 문제들 통계 누적
            List<SolvedProblemDto> solvedProblemDtos = userSolvedProblemService.findAllTodayUserSolvedProblem(stat.getBojHandle());
            for (SolvedProblemDto solvedProblemDto : solvedProblemDtos) {
                stat.addStatDaily(solvedProblemDto);
            }

            // 통계 저장
            userProblemStatisticsRepository.save(stat);
        }

        // 2.유저가 오늘 번 포인트를 계산한다.
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findAll();

        // 각각 유저의 정보를 가져와서 통계를 만들고 저장한다.
        for (UserStatistics stat: userStatisticsList) {
            // 유저가 오늘 번 포인트 통계 초기화
            stat.initDaily();

            // 유저의 랜덤 스트릭 가져오기
            UserRandomStreak userRandomStreak = userRandomStreakService.findUserRandomStreak(stat.getBojHandle());

            // 오늘 푼 문제들 포인트 통계 누적
            List<SolvedProblemDto> solvedProblemDtos = userSolvedProblemService.findAllTodayUserSolvedProblem(stat.getBojHandle());
            for (SolvedProblemDto solvedProblemDto : solvedProblemDtos) {

                // 랜덤문제라면 포인트 2배
                if (userRandomStreak.getTodayRandomProblemId().equals(solvedProblemDto.getProblemId())) {
                    stat.addStatDaily(solvedProblemDto, solvedProblemDto.getPoint() * 2);
                } else {
                    stat.addStatDaily(solvedProblemDto, solvedProblemDto.getPoint());
                }
            }

            // 통계 저장
            userStatisticsRepository.save(stat);
        }
    }
}
