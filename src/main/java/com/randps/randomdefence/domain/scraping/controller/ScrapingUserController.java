package com.randps.randomdefence.domain.scraping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.scraping.service.ScrapingUserLogService;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import com.randps.randomdefence.domain.user.service.UserSolvedProblemService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/scraping/user")
public class ScrapingUserController {

    private final UserSolvedProblemService userSolvedProblemService;

    private final UserInfoService userInfoService;

    private final UserRandomStreakService userRandomStreakService;

    private final ScrapingUserLogService scrapingUserLogService;

    /*
     * 특정 유저의 데이터를 스크래핑 한다.
     * 단, 20분에 한 번만 가능
     */
    @PostMapping("/")
    public ResponseEntity<Map<String, String>> scrapingUserData(@Param("bojHandle") String bojHandle) throws JsonProcessingException {
        if (!scrapingUserLogService.isPossible(bojHandle)) {
            HttpHeaders responseHeaders = new HttpHeaders();
            HttpStatus httpStatus = HttpStatus.TOO_EARLY;

            Map<String, String> map = new HashMap<>();
            map.put("type", httpStatus.getReasonPhrase());
            map.put("code", "425");
            map.put("message", "20분에 한 번만 요청이 가능합니다.");
            return new ResponseEntity<>(map, responseHeaders, httpStatus);
        }

        userSolvedProblemService.crawlTodaySolvedProblem(bojHandle); // 특정 유저의 맞았습니다를 크롤링해서 해결한 문제 DB를 업데이트한다.
        userInfoService.crawlUserInfo(bojHandle); // 특정 유저의 프로필 정보를 크롤링해서 DB를 업데이트한다.
        userRandomStreakService.solvedCheck(bojHandle); // 특정 유저의 오늘의 추첨 랜덤 문제 풀었는지 여부를 체크하고 DB를 업데이트한다.
        userInfoService.updateUserInfo(bojHandle); // 특정 유저의 문제 풀었는지 여부를 체크해서 저장한다.

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}
