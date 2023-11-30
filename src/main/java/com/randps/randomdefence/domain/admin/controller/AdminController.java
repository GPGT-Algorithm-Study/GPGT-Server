package com.randps.randomdefence.domain.admin.controller;

import com.randps.randomdefence.domain.admin.service.AdminService;
import com.randps.randomdefence.domain.admin.service.AdminStreakService;
import com.randps.randomdefence.domain.event.dto.EventPointPublishRequest;
import com.randps.randomdefence.domain.event.dto.EventPointUpdateRequest;
import com.randps.randomdefence.domain.event.service.EventPointService;
import com.randps.randomdefence.domain.item.dto.ItemDto;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    private final AdminStreakService adminStreakService;

    private final EventPointService eventPointService;

    /**
     * 아이템 가격 조정
     */
    @PutMapping("/item/update/cost")
    public ItemDto updateItemCost(@Param("itemId") Long itemId, @Param("cost") Integer cost) {
        return adminService.updateCost(itemId, cost);
    }

    /**
     * 문제 풀이 보너스 이벤트 생성
     *
     * [포인트 배율]
     * *--------*
     * 0   -> 기본 상태
     * 0.3 -> (기본 + 기본*0.3) 1.3배
     * 1   -> (기본 + 기본) 2배
     * 2.3 -> (기본 + 기본*2.3) 3.3배
     * *--------*
     */
    @PostMapping("/point/bonus")
    public ResponseEntity<Map<String,String>> eventPointBonusPublish(@RequestBody EventPointPublishRequest eventPointPublishRequest) {

        // 포인트 배율 이벤트 생성
        eventPointService.publishEventPoint(eventPointPublishRequest);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "포인트 이벤트를 성공적으로 생성했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /**
     * 문제 풀이 보너스 이벤트 수정
     */
    @PutMapping("/point/bonus")
    public ResponseEntity<Map<String,String>> eventPointBonusUpdate(@RequestBody EventPointUpdateRequest eventPointUpdateRequest) {

        // 포인트 배율 이벤트 생성
        eventPointService.updateEventPoint(eventPointUpdateRequest);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "포인트 이벤트를 성공적으로 수정했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /**
     * 문제 풀이 보너스 이벤트 삭제
     */
    @DeleteMapping("/point/bonus")
    public ResponseEntity<Map<String,String>> eventPointBonusDelete(@RequestParam("eventId") Long eventId) {

        // 포인트 배율 이벤트 삭제
        eventPointService.deleteEventPoint(eventId);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "포인트 이벤트를 성공적으로 삭제했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
