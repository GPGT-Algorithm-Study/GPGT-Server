package com.randps.randomdefence.domain.event.controller;

import com.randps.randomdefence.domain.event.dto.EventPointDto;
import com.randps.randomdefence.domain.event.service.EventPointSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/event/point")
public class EventPointSearchController {

    private final EventPointSearchService eventPointSearchService;

    /**
     * 모든 보너스 포인트 이벤트 조회
     */
    @GetMapping("/all")
    public List<EventPointDto> findAllEventPoint() {
        return eventPointSearchService.findAllEventPoint();
    }

    /**
     * 현재 적용중인 모든 보너스 포인트 이벤트 조회
     */
    @GetMapping("/all/valid")
    public List<EventPointDto> findAllValidEventPoint() {
        return eventPointSearchService.findAllValidEventPoint();
    }
}
