package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.randps.randomdefence.global.component.crawler.BojWebCrawler.is6AmAfter;

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

    @Override
    public List<UserSolvedProblem> findAllTodaySolvedProblem() {
        // 오늘의 기준을 만든다.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDateTime;
        if (is6AmAfter(now.getHour())) {
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        } else {
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }

        return userSolvedProblemJpaRepository.findAllByCreatedDateAfter(startOfDateTime);
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        userSolvedProblemJpaRepository.deleteAllByBojHandle(bojHandle);
    }
}
