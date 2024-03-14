package com.randps.randomdefence.domain.problem.mock;

import com.randps.randomdefence.domain.problem.domain.Problem;
import com.randps.randomdefence.domain.problem.service.port.ProblemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeProblemRepository implements ProblemRepository {

    private final List<Problem> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;


    @Override
    public Optional<Problem> findByProblemId(Integer problemId) {
        return data.stream().filter(item -> item.getProblemId().equals(problemId)).findAny();
    }

    @Override
    public Problem save(Problem problem) {
        if (problem.getId() == null || problem.getId() == 0L) {
            autoIncreasingCount++;
            Problem newProblem = Problem.builder()
                    .id(autoIncreasingCount)
                    .problemId(problem.getProblemId())
                    .titleKo(problem.getTitleKo())
                    .isSolvable(problem.getIsSolvable())
                    .isPartial(problem.getIsPartial())
                    .acceptedUserCount(problem.getAcceptedUserCount())
                    .level(problem.getLevel())
                    .votedUserCount(problem.getVotedUserCount())
                    .sprout(problem.getSprout())
                    .givesNoRating(problem.getGivesNoRating())
                    .isLevelLocked(problem.getIsLevelLocked())
                    .averageTries(problem.getAverageTries())
                    .official(problem.getOfficial())
                    .tags(problem.getTags())
                    .build();
            data.add(newProblem);
            return newProblem;
        } else {
            data.removeIf(item -> item.getId().equals(problem.getId()));
            Problem newProblem = Problem.builder()
                    .id(problem.getId())
                    .problemId(problem.getProblemId())
                    .titleKo(problem.getTitleKo())
                    .isSolvable(problem.getIsSolvable())
                    .isPartial(problem.getIsPartial())
                    .acceptedUserCount(problem.getAcceptedUserCount())
                    .level(problem.getLevel())
                    .votedUserCount(problem.getVotedUserCount())
                    .sprout(problem.getSprout())
                    .givesNoRating(problem.getGivesNoRating())
                    .isLevelLocked(problem.getIsLevelLocked())
                    .averageTries(problem.getAverageTries())
                    .official(problem.getOfficial())
                    .tags(problem.getTags())
                    .build();
            data.add(newProblem);
            return newProblem;
        }
    }
}
