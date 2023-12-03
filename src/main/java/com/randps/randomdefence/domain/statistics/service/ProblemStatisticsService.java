package com.randps.randomdefence.domain.statistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.statistics.domain.ProblemStatistics;
import com.randps.randomdefence.domain.statistics.domain.ProblemStatisticsRepository;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolvedRepository;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.domain.UserSolvedProblemRepository;
import com.randps.randomdefence.domain.user.service.UserAlreadySolvedService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProblemStatisticsService {

    private final UserRepository userRepository;

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
     * 가장 많은 유저가 푼 순서대로 문제 리스트를 반환한다.
     */
    @Transactional
    public List<ProblemStatistics> findMostSolvedProblems() {
        return problemStatisticsRepository.findAllByOrderBySolvedCountDesc();
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

        for (UserAlreadySolved alreadySolvedProblem : alreadySolvedProblems) {
            List<Integer> problemIds = alreadySolvedProblem.getAlreadySolvedList();
            for (Integer problemId : problemIds) {
                incrementProblemSolvedCount(problemId);
            }
        }
    }
}
