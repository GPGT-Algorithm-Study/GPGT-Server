package com.randps.randomdefence.domain.statistics.controller;

import com.randps.randomdefence.domain.statistics.dto.RecentlyTodaySolvedProblemResponseDto;
import com.randps.randomdefence.domain.statistics.service.RecentSolvedProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat/recent-solved-problem")
public class RecentSolvedProblemController {

    private final RecentSolvedProblemService recentSolvedProblemService;

    @GetMapping("/today")
    public List<RecentlyTodaySolvedProblemResponseDto> getTodayRecentSolvedProblems() {
        return recentSolvedProblemService.getTodayRecentSolvedProblems();
    }
}
