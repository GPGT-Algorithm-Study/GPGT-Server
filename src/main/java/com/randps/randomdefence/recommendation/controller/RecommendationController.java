package com.randps.randomdefence.recommendation.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.recommendation.dto.RecommendationResponse;
import com.randps.randomdefence.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recommend")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /*
     * 유저가 원하는 난이도 범위의 문제를 랜덤하게 추천한다. (1회 호출당 실제 solvedac의 API를 1회 호출함)
     */
    @GetMapping("")
    public RecommendationResponse getRecommendProblem(@Param("userId") String userId, @Param("start") String start, @Param("end") String end) {
        String query = recommendationService.makeQuery(userId, start, end);
        RecommendationResponse recommendationResponse = recommendationService.makeRecommend(query);

        return recommendationResponse;
    }

}
