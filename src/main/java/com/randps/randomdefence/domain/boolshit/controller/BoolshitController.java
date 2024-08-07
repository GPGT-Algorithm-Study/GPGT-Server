package com.randps.randomdefence.domain.boolshit.controller;

import com.randps.randomdefence.domain.boolshit.dto.BoolshitResponse;
import com.randps.randomdefence.domain.boolshit.service.BoolshitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/boolshit")
public class BoolshitController {

    private final BoolshitService boolshitService;

    /*
     * 유저의 가장 최근의 나의 한마디를 조회한다.
     */
    @GetMapping("/last")
    public BoolshitResponse findLast() {
        return boolshitService.findLast();
    }

    /*
     * 모든 유저의 나의 한마디를 조회한다.
     */
    @GetMapping("/all")
    public List<BoolshitResponse> findAll() {
        return boolshitService.findAll();
    }
}
