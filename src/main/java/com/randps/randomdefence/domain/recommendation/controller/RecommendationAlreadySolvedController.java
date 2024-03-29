package com.randps.randomdefence.domain.recommendation.controller;

import com.randps.randomdefence.domain.problem.dto.ProblemSolveJudgedDto;
import com.randps.randomdefence.domain.recommendation.service.RecommendationAlreadySolvedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recommend/solved")
public class RecommendationAlreadySolvedController {

    private final RecommendationAlreadySolvedService recommendationAlreadySolvedService;

    /*
     * 유저가 기존에 푼 문제중 하나를 랜덤하게 뽑아온다.
     */
    @GetMapping("/")
    public ProblemSolveJudgedDto getSolvedProblemRandom(@Param("bojHandle") String bojHandle) {
        return recommendationAlreadySolvedService.findOneRandomSolvedProblem(bojHandle);
    }

    /*
     * 문제 제목으로 머신러닝으로 비슷한 문제를 추천해준다.
     */
    @GetMapping("/recommend")
    public ResponseEntity<String> getRecommendProblemsRandom(@Param("title") String title) {
        return recommendationAlreadySolvedService.findRecommendProblems(title);
    }

    /*
     * 유저같은 클러스터의 유저들이 푼 문제들을 추천해준다.
     */
    @GetMapping("/clustering")
    public ResponseEntity<String> getClusteringProblems(@Param("bojHandle") String bojHandle) {
        return recommendationAlreadySolvedService.findClusterProblems(bojHandle);
    }
}
