package com.randps.randomdefence.domain.scraping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.service.UserGrassService;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import com.randps.randomdefence.domain.user.service.UserSolvedProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/scraping")
public class ScrapingController {

    private final UserSolvedProblemService userSolvedProblemService;

    private final UserInfoService userInfoService;

    private final UserRandomStreakService userRandomStreakService;

    private final UserGrassService userGrassService;

    private final UserStatisticsService userStatisticsService;

    private final TeamSettingService teamSettingService;

    /*
     * 유저가 오늘 푼 문제 스크래핑 (기존 데이터와 중복 제거 포함, 단 옛날에 똑같은 문제를 푼적 있다면 중복 제거되지 않음)
     */
    @GetMapping("/user/today-solved")
    public ResponseEntity<Map<String, String>> scrapUserTodaySolvedList(@Param("bojHandle") String bojHandle) throws JsonProcessingException {
        userSolvedProblemService.crawlTodaySolvedProblem(bojHandle);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 모든 유저가 오늘 푼 문제 스크래핑 (기존 데이터와 중복 제거 포함, 단 옛날에 똑같은 문제를 푼적 있다면 중복 제거되지 않음)
     */
    @GetMapping("/user/today-solved/all")
    public ResponseEntity<Map<String, String>> scrapUserTodaySolvedListAll() throws JsonProcessingException {
        userSolvedProblemService.crawlTodaySolvedProblemAll();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 유저의 프로필 정보 스크래핑 (기존에 유저가 등록되어 있어야함.)
     */
    @GetMapping("/user/info")
    public ResponseEntity<Map<String, String>> scrapUserInfo(@Param("bojHandle") String bojHandle) throws JsonProcessingException {
        userInfoService.crawlUserInfo(bojHandle);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 모든 유저의 프로필 정보 스크래핑 (기존에 유저가 등록되어 있어야함.)
     */
    @GetMapping("/user/info/all")
    public ResponseEntity<Map<String, String>> scrapUserInfo() throws JsonProcessingException {
        userInfoService.crawlUserInfoAll();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 정해진 시간마다 실행되는 스크래핑 메서드 (30분 간격)
     */
    @GetMapping("/cron/batch")
    public ResponseEntity<Map<String, String>> cronBatch() throws JsonProcessingException {
        userInfoService.crawlUserInfoAll(); // 모든 유저의 프로필 정보를 크롤링해서 DB를 업데이트한다.
        userSolvedProblemService.crawlTodaySolvedProblemAll(); // 모든 유저의 맞았습니다를 크롤링해서 해결한 문제 DB를 업데이트한다.
        userRandomStreakService.solvedCheckAll(); // 모든 유저의 오늘의 추첨 랜덤 문제 풀었는지 여부를 체크하고 DB를 업데이트한다.

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 정해진 시간마다 실행되는 스크래핑 메서드 (매일 새벽 6시)
     * 주의사항 : 2일치 잔디가 심어져있지 않다면, 잔디 NotFoundException이 뜬다.
     *          -> 이는 유저 생성시 전 일의 잔디를 생성해서 해결했음. 단 유저 생성 메서드 외의 방법으로 유저 생성 시 에러 유발 가능
     */
    @GetMapping("/cron/batch/daily")
    public ResponseEntity<Map<String, String>> cronBatchDaily() throws JsonProcessingException {
        userGrassService.makeTodayGrassAll(); // 모든 유저의 오늘 잔디를 생성한다.
        userRandomStreakService.makeUpUserRandomProblemAll(); // 모든 유저의 랜덤 문제를 1문제를 뽑아 저장한다.
        userRandomStreakService.streakCheckAll(); // 모든 유저에 대해 유저의 전일 문제가 풀리지 않았다면 랜덤 스트릭을 끊는다.
        userInfoService.checkAllUserSolvedStreak(); // 유저의 스트릭이 끊겼다면(랜덤 스트릭이 아닌 Solvedac 스트릭) 경고를 1회 올린다.
        userStatisticsService.initAllDailyStat(); // 모든 유저의 일간 통계를 초기화한다.

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
