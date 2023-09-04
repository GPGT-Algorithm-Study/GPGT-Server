package com.randps.randomdefence.domain.boolshit.controller;

import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;
import com.randps.randomdefence.domain.boolshit.service.BoolshitServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/boolshit")
public class BoolshitControllerV2 {

    private final BoolshitServiceV2 boolshitService;

    /**
     * 유저의 가장 최근의 나의 한마디를 조회한다. (Querydsl)
     */
    @GetMapping("/last")
    public BoolshitLastResponse findLast() {
        return boolshitService.findLast();
    }
}
