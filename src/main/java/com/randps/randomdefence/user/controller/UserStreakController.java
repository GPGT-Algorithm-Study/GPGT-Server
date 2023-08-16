package com.randps.randomdefence.user.controller;

import com.randps.randomdefence.user.domain.UserRandomStreak;
import com.randps.randomdefence.user.dto.UserGrassDto;
import com.randps.randomdefence.user.dto.UserRandomStreakDto;
import com.randps.randomdefence.user.dto.UserRandomStreakResponse;
import com.randps.randomdefence.user.service.UserGrassService;
import com.randps.randomdefence.user.service.UserRandomStreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/streak")
public class UserStreakController {

    private final UserGrassService userGrassService;

    private final UserRandomStreakService userRandomStreakService;

    /*
     * 특정 유저의 문제를 해결한 날의 모든 잔디를 반환한다.
     */
    @GetMapping("/grass")
    public List<UserGrassDto> findAllGrass(@Param("bojHandle") String bojHandle) {
        UserRandomStreak userRandomStreak = userRandomStreakService.findUserRandomStreak(bojHandle);

        return userGrassService.findUserGrassList(userRandomStreak);
    }

    /*
     * 특정 유저의 랜덤 스트릭 정보를 반환한다.
     */
    @GetMapping("/streak")
    public UserRandomStreakResponse findStreak(@Param("bojHandle") String bojHandle) {
        return userRandomStreakService.findUserRandomStreakToResponseForm(bojHandle);
    }

    /*
     * 모든 유저의 랜덤 스트릭 정보를 반환한다.
     */
    @GetMapping("/streak/all")
    public List<UserRandomStreakResponse> findStreakAll() {
        return userRandomStreakService.findAllUserRandomStreak();
    }

    /*
     * 특정 유저의 랜덤 스트릭 문제 범위를 업데이트 한다.
     */
    @PutMapping("/streak/level")
    public ResponseEntity<Map<String, String>> findStreakAll(@Param("bojHandle") String bojHandle, @Param("start") String start, @Param("end") String end) {
        userRandomStreakService.updateLevel(bojHandle, start, end);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청이 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 모든 유저의 오늘 잔디를 생성합니다. (서버용)
     */
    @PostMapping("/grass/all")
    public ResponseEntity<Map<String, String>> makeAllGrass() {
        userGrassService.makeTodayGrassAll();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청이 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 모든 유저의 오늘 랜덤 문제를 할당한다. (서버용)
     */
    @PostMapping("/make-random-problem")
    public ResponseEntity<Map<String, String>> makeRandomProblem() {
        userRandomStreakService.makeUpUserRandomProblemAll();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청이 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
