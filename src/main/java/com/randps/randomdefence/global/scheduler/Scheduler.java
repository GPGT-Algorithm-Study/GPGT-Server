package com.randps.randomdefence.global.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        userInfoService.checkAllUserSolvedStreak(); // 유저의 스트릭이 끊겼다면(랜덤 스트릭이 아닌 Solvedac 스트릭) 경고를 1회 올린다.
    }
}
