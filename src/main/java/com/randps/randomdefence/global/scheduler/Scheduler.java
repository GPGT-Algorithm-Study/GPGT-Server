package com.randps.randomdefence.global.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.statistics.service.TeamStatisticsService;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.service.UserGrassService;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import com.randps.randomdefence.domain.user.service.UserSolvedProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@SpringBootApplication
@EnableScheduling
public class Scheduler {

    private final UserInfoService userInfoService;

    private final UserSolvedProblemService userSolvedProblemService;

    private final UserRandomStreakService userRandomStreakService;

    private final UserGrassService userGrassService;

    private final UserStatisticsService userStatisticsService;

    private final TeamSettingService teamSettingService;

    private final TeamService teamService;

    /*
     * 정해진 시간마다 실행되는 스크래핑 메서드 (매 20분 간격)
     */
    @Scheduled(cron = "0 0/20 * * * *")
    public void everyTermJob() throws JsonProcessingException {
        userInfoService.crawlUserInfoAll(); // 모든 유저의 프로필 정보를 크롤링해서 DB를 업데이트한다.
        userSolvedProblemService.crawlTodaySolvedProblemAll(); // 모든 유저의 맞았습니다를 크롤링해서 해결한 문제 DB를 업데이트한다.
        userRandomStreakService.solvedCheckAll(); // 모든 유저의 오늘의 추첨 랜덤 문제 풀었는지 여부를 체크하고 DB를 업데이트한다.
    }

    /*
     * 정해진 시간마다 실행되는 스크래핑 메서드 (하루 간격, 매일 새벽 6시 25분)
     */
    @Scheduled(cron = "0 25 6 * * *")
    public void everyDayTermJob() throws JsonProcessingException {
        userGrassService.makeTodayGrassAll(); // 모든 유저의 오늘 잔디를 생성한다.
        userRandomStreakService.makeUpUserRandomProblemAll(); // 모든 유저의 랜덤 문제를 1문제를 뽑아 저장한다.
        userRandomStreakService.streakCheckAll(); // 모든 유저에 대해 유저의 전일 문제가 풀리지 않았다면 랜덤 스트릭을 끊는다.
        userInfoService.checkAllUserSolvedStreak(); // 유저드스트릭이 끊겼다면(랜덤 스트릭이 아닌 Solvedac 스트릭) 경고를 1회 올린다.
        userStatisticsService.initAllDailyStat(); // 모든 유저의 일간 통계를 초기화한다.
    }

    /*
     * 주간 초기화 메서드 (매 주 월요일 새벽 6시 26분)
     */
    @Scheduled(cron = "0 26 6 * * 1")
    public void weekInitJob() {
        userStatisticsService.initAllDailyStat(); // 모든 유저의 일간 통계를 초기화한다.
        userStatisticsService.initAllWeeklyStat(); // 모든 유저의 주간 통계를 초기화한다.
        teamSettingService.initWeekly(); // 팀 포인트 주간 초기화
        teamSettingService.setUsers(); // 모든 유저 팀 할당
        teamService.weeklyTeamPointDistribution(); // 승리 팀에게 승리 포인트 지급
    }
}
