package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.domain.UserSolvedProblemRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSolvedJudgeService {

    private final UserAlreadySolvedService userAlreadySolvedService;

    private final UserSolvedProblemRepository userSolvedProblemRepository;

    /*
     * 유저가 문제를 풀었는지 여부를 반환한다.
     */
    @Transactional
    public boolean isSolved(String bojHandle, Integer problemId) {
        Optional<UserSolvedProblem> problem  = userSolvedProblemRepository.findByBojHandleAndProblemId(bojHandle, problemId);
        return problem.isPresent() || userAlreadySolvedService.isSolved(bojHandle, problemId);
    }

}
