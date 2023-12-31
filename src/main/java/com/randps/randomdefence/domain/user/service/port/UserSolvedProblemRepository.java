package com.randps.randomdefence.domain.user.service.port;

import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import java.util.List;
import java.util.Optional;

public interface UserSolvedProblemRepository {

    List<UserSolvedProblem> findAllByBojHandle(String bojHandle);
    Optional<UserSolvedProblem> findByBojHandleAndProblemId(String bojHandle, Integer problemId);
    List<UserSolvedProblem> findAll();
    List<UserSolvedProblem> saveAll(List<UserSolvedProblem> userSolvedProblems);

}
