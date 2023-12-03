package com.randps.randomdefence.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.user.service.UserSejongCrawlingService;
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
@RequestMapping("/api/v1/user/sejong")
public class UserSejongController {

    private final UserSejongCrawlingService userSejongCrawlingService;

    @PostMapping("/init/all")
    public ResponseEntity<Map<String, String>> initAllSejongUsers() throws JsonProcessingException {
        Integer userNumber = userSejongCrawlingService.crawlingAllUserInSejong();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", userNumber + "명의 유저를 성공적으로 크롤링했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @PostMapping("/init/all/solved")
    public ResponseEntity<Map<String, String>> initAllSejongUsersSolvedProblems(@Param("half") Integer half) throws JsonProcessingException {
        userSejongCrawlingService.crawlingAllUserSolvedProblemsInSejong(half);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "유저들이 이미 푼 문제를 성공적으로 크롤링했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @PostMapping("/save/all/info")
    public ResponseEntity<Map<String, String>> saveAllSejongUsersInfo(@Param("idx") Integer idx, @Param("total") Integer total) throws JsonProcessingException {
        Integer userNumber = userSejongCrawlingService.saveAllUserInfoInSejong(idx, total);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", userNumber + "명의 유저를 성공적으로 저장했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @PostMapping("/init/all/info")
    public ResponseEntity<Map<String, String>> initAllSejongUsersInfo() throws JsonProcessingException {
        Integer userNumber = userSejongCrawlingService.crawlingAllSejongUserInfo();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", userNumber + "명의 유저를 성공적으로 저장했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
