package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class UserSolvedJudgeService {

    private final UserAlreadySolvedService userAlreadySolvedService;

    private final UserSolvedProblemRepository userSolvedProblemRepository;

    /*
     * 유저가 문제를 풀었는지 여부를 반환한다.
     */
    public boolean isSolved(String bojHandle, Integer problemId) {
        Optional<UserSolvedProblem> problem  = userSolvedProblemRepository.findByBojHandleAndProblemId(bojHandle, problemId);
        return problem.isPresent() || userAlreadySolvedService.isSolved(bojHandle, problemId);
    }

    /*
     * 유저가 문제를 풀었는지 여부를 반환한다.
     */
    public boolean isSolved(String bojHandle, ProblemDto problemDto) {
        Optional<UserSolvedProblem> problem  = userSolvedProblemRepository.findByBojHandleAndProblemId(bojHandle, problemDto.getProblemId());
        return problem.isPresent() || userAlreadySolvedService.isSolved(bojHandle, problemDto.getProblemId());
    }

}
