package com.randps.randomdefence.domain.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminStreakService {

    private final UserRandomStreakService userRandomStreakService;

    private final UserInfoService userInfoService;

    private final UserStatisticsService userStatisticsService;

    @Transactional
    public void streakInit() throws JsonProcessingException {
        userRandomStreakService.streakCheckAll(); // 모든 유저에 대해 유저의 전일 문제가 풀리지 않았다면 랜덤 스트릭을 끊는다.
        userInfoService.checkAllUserSolvedStreak(); // 모든 유저에 대해 전 일 스트릭이 끊겼다면(랜덤 스트릭이 아닌 Solvedac 스트릭) 경고를 1회 올린다.
        userStatisticsService.initAllDailyStat(); // 모든 유저의 일간 통계를 초기화한다.
    }

}
