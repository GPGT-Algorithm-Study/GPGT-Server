package com.randps.randomdefence.scraping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.problem.service.ProblemService;
import com.randps.randomdefence.scraping.service.ScrapingService;
import com.randps.randomdefence.user.service.UserInfoService;
import com.randps.randomdefence.userSolvedProblem.service.UserSolvedProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/scraping")
public class ScrapingController {

    private final UserSolvedProblemService userSolvedProblemService;

    private final UserInfoService userInfoService;

    private final ScrapingService scrapingService;

    private final ProblemService problemService;

    /*
     * 유저가 오늘 푼 문제 스크래핑 (기존 데이터와 중복 제거 포함, 단 옛날에 똑같은 문제를 푼적 있다면 중복 제거되지 않음)
     */
    @GetMapping("/user/today-solved")
    public HttpStatus scrapUserTodaySolvedList(@Param("bojHandle") String bojHandle) throws JsonProcessingException {
        userSolvedProblemService.crawlTodaySolvedProblem(bojHandle);

        return HttpStatus.OK;
    }

    /*
     * 유저의 프로필 정보 스크래핑 (기존에 유저가 등록되어 있어야함.)
     */
    @GetMapping("/user/info")
    public HttpStatus scrapUserInfo(@Param("bojHandle") String bojHandle) throws JsonProcessingException {
        userInfoService.crawlUserInfo(bojHandle);

        return HttpStatus.OK;
    }

}
