package com.randps.randomdefence.domain.statistics.controller;

import com.randps.randomdefence.domain.statistics.domain.UserStatistics;
import com.randps.randomdefence.domain.statistics.dto.YesterdayUnsolvedUserDto;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsServiceV2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat/user")
public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    private final UserStatisticsServiceV2 userStatisticsServiceV2;

    /*
     * 유저 통계를 조회한다.
     */
    @GetMapping("")
    public UserStatistics findUserStat(@Param("bojHandle") String bojHandle) {
        UserStatistics userStatistics = userStatisticsService.findByBojHandle(bojHandle);

        return userStatistics;
    }

    /*
     * 유저의 일간 통계를 초기화한다.
     */
    @DeleteMapping("")
    public ResponseEntity<Map<String, String>> initUserStat(@Param("bojHandle") String bojHandle) {
        userStatisticsService.initAllDailyStat();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 유저의 일간 통계를 다시 구성한다.
     */
    @PostMapping("/integrity-check")
    public ResponseEntity<Map<String, String>> composeAllUserStatDaily() {
        userStatisticsService.integrityCheckTodayStatistics();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청을 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 어제 문제를 풀지 않은 유저 리스트를 반환한다. (Admin)
     */
    @GetMapping("/yesterday-unsolved-users")
    public List<YesterdayUnsolvedUserDto> findAllYesterdayUnsolvedUsers() {
        return userStatisticsServiceV2.getAllYesterdayUnsolvedUser();
    }
}
