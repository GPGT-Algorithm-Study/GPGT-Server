package com.randps.randomdefence.domain.statistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.statistics.dto.MostSolvedProblemDto;
import com.randps.randomdefence.domain.statistics.service.ProblemStatisticsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stat/problem")
public class ProblemStatisticsController {

    private final ProblemStatisticsService problemStatisticsService;

    /*
     * 가장 많은 사람이 푼 문제 순으로 문제 리스트를 반환한다.
     */
    @GetMapping("/most-solved")
    public List<MostSolvedProblemDto> findMostSolvedProblems() {
        return problemStatisticsService.findMostSolvedProblems();
    }

    /*
     * 모든 유저가 푼 문제에 대해서 풀이 횟수를 카운팅한다.
     */
    @PostMapping("/calculate")
    public void incrementAllProblemSolvedCount() throws JsonProcessingException {
        problemStatisticsService.incrementAllProblemSolvedCount();
    }

    /*
     * 모든 유저가 푼 문제에 대해서 풀이 횟수를 카운팅한다.
     */
    @PostMapping("/calculate/already")
    public void incrementAllProblemSolvedCountByAlreadyData() throws JsonProcessingException {
        problemStatisticsService.incrementAllProblemSolvedCountByAlreadyData();
    }

}
