package com.randps.randomdefence.domain.problem.controller;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.dto.ProblemSolveJudgedDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/problem")
public class ProblemController {

    private final ProblemService problemService;

    /**
     * 문제Id로 문제 조회
     */
    @GetMapping("/find")
    public ProblemDto findByProblemId(@Param("problemId") Integer problemId) {
        return problemService.findProblem(problemId);
    }

    /**
     * 유저 bojHandle과 문제Id로 문제 조회 (문제 해결 여부 포함)
     */
    @GetMapping("/user/find")
    public ProblemSolveJudgedDto findByProblemIdAndBojHandle(@Param("bojHandle") String bojHandle, @Param("problemId") Integer problemId) {
        return problemService.findProblemByBojHandle(bojHandle, problemId);
    }

}
