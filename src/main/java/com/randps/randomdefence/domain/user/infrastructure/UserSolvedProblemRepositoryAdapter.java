package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSolvedProblemRepositoryAdapter implements UserSolvedProblemRepository {

    private final UserSolvedProblemJpaRepository userSolvedProblemJpaRepository;

    @Override
    public List<UserSolvedProblem> findAllByBojHandle(String bojHandle) {
        return userSolvedProblemJpaRepository.findAllByBojHandle(bojHandle);
    }

    @Override
    public Optional<UserSolvedProblem> findByBojHandleAndProblemId(String bojHandle, Integer problemId) {
        return userSolvedProblemJpaRepository.findByBojHandleAndProblemId(bojHandle, problemId);
    }

    @Override
    public List<UserSolvedProblem> findAll() {
        return userSolvedProblemJpaRepository.findAll();
    }

    @Override
    public List<UserSolvedProblem> saveAll(List<UserSolvedProblem> userSolvedProblems) {
        return userSolvedProblemJpaRepository.saveAll(userSolvedProblems);
    }
}
