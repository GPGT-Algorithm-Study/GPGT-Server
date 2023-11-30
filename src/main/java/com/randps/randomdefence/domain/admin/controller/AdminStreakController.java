package com.randps.randomdefence.domain.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.admin.service.AdminStreakService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/streak")
public class AdminStreakController {

    private final AdminStreakService adminStreakService;


    /**
     * 전체 유저의 스트릭 정보를 다음날로 넘기고 갱신한다.
     */
    @PostMapping("/init")
    public ResponseEntity<Map<String,String>> streakInit() throws JsonProcessingException {

        adminStreakService.streakInit();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "스트릭 초기화를 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
