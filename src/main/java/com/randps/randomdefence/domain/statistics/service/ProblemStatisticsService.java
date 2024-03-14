package com.randps.randomdefence.domain.statistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.statistics.domain.ProblemStatistics;
import com.randps.randomdefence.domain.statistics.domain.ProblemStatisticsRepository;
import com.randps.randomdefence.domain.statistics.dto.MostSolvedProblemDto;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.service.UserAlreadySolvedService;
import com.randps.randomdefence.domain.user.service.port.UserAlreadySolvedRepository;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProblemStatisticsService {

    private final UserAlreadySolvedRepository userAlreadySolvedRepository;

    private final UserSolvedProblemRepository userSolvedProblemRepository;

    private final UserAlreadySolvedService userAlreadySolvedService;

    private final ProblemStatisticsRepository problemStatisticsRepository;

    /*
     * 문제의 풀이 횟수를 1늘린다.
     */
    @Transactional
    public void incrementProblemSolvedCount(Integer problemId) {
        ProblemStatistics problemStatistics = problemStatisticsRepository.findByProblemId(problemId)
                .orElse(new ProblemStatistics(problemId, 0L));
        problemStatistics.incrementSolvedCount();
        problemStatisticsRepository.save(problemStatistics);
    }

    /*
     * 모든 문제의 풀이 횟수를 각각 카운팅된대로 늘린다.
     */
    @Transactional
    public void incrementProblemSolvedCount(int[] problemCount) {
        List<ProblemStatistics> problemStatistics = new ArrayList<>();

        for (int i = 1000; i < problemCount.length; i++) {
            if (problemCount[i] == 0) continue;
            problemStatistics.add(new ProblemStatistics(i, (long) problemCount[i]));
        }
        problemStatisticsRepository.saveAll(problemStatistics);
    }

    /*
     * 가장 많은 유저가 푼 순서대로 문제 리스트를 반환한다.
     */
    public List<MostSolvedProblemDto> findMostSolvedProblems() {
        List<ProblemStatistics> problems = problemStatisticsRepository.findTop50ByOrderBySolvedCountDesc();
        List<MostSolvedProblemDto> mostSolvedProblems = new ArrayList<>();

        for (ProblemStatistics problem : problems) {
            mostSolvedProblems.add(MostSolvedProblemDto.builder()
                    .problemId(problem.getProblemId())
                    .solvedCount(problem.getSolvedCount())
                    .build());
        }

        return mostSolvedProblems;
    }

    /*
     * 모든 유저가 푼 문제에 대해서 풀이 횟수를 카운팅한다.
     */
    @Transactional
    public void incrementAllProblemSolvedCount() throws JsonProcessingException {
        problemStatisticsRepository.deleteAll();
        userAlreadySolvedService.saveAllScrapingData();
        List<UserSolvedProblem> solvedProblems = userSolvedProblemRepository.findAll();
        List<UserAlreadySolved> alreadySolvedProblems = userAlreadySolvedRepository.findAll();

        for (UserSolvedProblem solvedProblem : solvedProblems) {
            incrementProblemSolvedCount(solvedProblem.getProblemId());
        }

        for (UserAlreadySolved alreadySolvedProblem : alreadySolvedProblems) {
            List<Integer> problemIds = alreadySolvedProblem.getAlreadySolvedList();
            for (Integer problemId : problemIds) {
                incrementProblemSolvedCount(problemId);
            }
        }
    }


    /*
     * 모든 유저가 푼 문제에 대해서 풀이 횟수를 카운팅한다.
     */
    @Transactional
    public void incrementAllProblemSolvedCountByAlreadyData() {
        problemStatisticsRepository.deleteAll();
        List<UserAlreadySolved> alreadySolvedProblems = userAlreadySolvedRepository.findAll();
        int[] problemCount = new int[35000];

        for (UserAlreadySolved alreadySolvedProblem : alreadySolvedProblems) {
            List<Integer> problemIds = alreadySolvedProblem.getAlreadySolvedList();
            for (Integer problemId : problemIds) {
                problemCount[problemId]++;
            }
        }
        incrementProblemSolvedCount(problemCount);
    }
}
