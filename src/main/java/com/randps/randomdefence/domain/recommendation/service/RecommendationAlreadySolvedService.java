package com.randps.randomdefence.domain.recommendation.service;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.dto.ProblemSolveJudgedDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolvedRepository;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecommendationAlreadySolvedService {

    private final ProblemService problemService;

    private final UserAlreadySolvedRepository userAlreadySolvedRepository;

    @Transactional
    public ProblemSolveJudgedDto findOneRandomSolvedProblem(String bojHandle) {
        UserAlreadySolved userAlreadySolved = userAlreadySolvedRepository.findByBojHandle(bojHandle).orElseThrow(() -> new NoSuchElementException("유저가 기존에 푼 문제가 존재하지 않습니다."));
        List<Integer> solvedProblems = userAlreadySolved.getAlreadySolvedList();
        ProblemDto problemDto = problemService.findProblem(solvedProblems.get((int)Math.round(Math.random() * (solvedProblems.size() - 1))));
        return new ProblemSolveJudgedDto(problemDto, true);
    }

}
