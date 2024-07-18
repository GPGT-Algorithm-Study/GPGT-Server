package com.randps.randomdefence.domain.user.mock;

import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeUserSolvedProblemRepository implements UserSolvedProblemRepository {

    private final List<UserSolvedProblem> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public List<UserSolvedProblem> findAllByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserSolvedProblem> findByBojHandleAndProblemId(String bojHandle, Integer problemId) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle) && item.getProblemId().equals(problemId)).findAny();
    }

    @Override
    public List<UserSolvedProblem> findAll() {
        return data;
    }

    @Override
    public List<UserSolvedProblem> saveAll(List<UserSolvedProblem> userSolvedProblems) {
        List<UserSolvedProblem> result = new ArrayList<>();
        for (UserSolvedProblem userSolvedProblem : userSolvedProblems) {
            if (userSolvedProblem.getId() == null || userSolvedProblem.getId() == 0L) {
                autoIncreasingCount++;
                UserSolvedProblem newUserSolvedProblem = UserSolvedProblem.builder()
                        .id(autoIncreasingCount)
                        .problemId(userSolvedProblem.getProblemId())
                        .bojHandle(userSolvedProblem.getBojHandle())
                        .title(userSolvedProblem.getTitle())
                        .dateTime(userSolvedProblem.getDateTime())
                        .language(userSolvedProblem.getLanguage())
                        .build();
                data.add(newUserSolvedProblem);
                result.add(newUserSolvedProblem);
            } else {
                data.removeIf(item -> item.getId().equals(userSolvedProblem.getId()));
                UserSolvedProblem newUserSolvedProblem = UserSolvedProblem.builder()
                        .id(userSolvedProblem.getId())
                        .problemId(userSolvedProblem.getProblemId())
                        .bojHandle(userSolvedProblem.getBojHandle())
                        .title(userSolvedProblem.getTitle())
                        .dateTime(userSolvedProblem.getDateTime())
                        .language(userSolvedProblem.getLanguage())
                        .build();
                data.add(newUserSolvedProblem);
                result.add(newUserSolvedProblem);
            }
        }
        return result;
    }

    @Override
    public List<UserSolvedProblem> findAllTodaySolvedProblem() {
        return List.of();
    }

    @Override
    public void deleteAllByBojHandle(String bojHandle) {
        data.removeIf(item -> item.getBojHandle().equals(bojHandle));
    }
}
