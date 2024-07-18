package com.randps.randomdefence.domain.statistics.service;

import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.statistics.dto.RecentlyTodaySolvedProblemResponseDto;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecentSolvedProblemService {

    private final UserSolvedProblemRepository userSolvedProblemRepository;

    private final ProblemService problemService;

    private final UserRepository userRepository;

    /*
     * 오늘 해결된 문제를 최신순으로 가져온다.
     */
    @Transactional
    public List<RecentlyTodaySolvedProblemResponseDto> getTodayRecentSolvedProblems() {

        // 오늘 해결된 문제들을 가져온다.
        List<UserSolvedProblem> todaySolvedProblems = userSolvedProblemRepository.findAllTodaySolvedProblem();

        // 문제들을 최신순으로 정렬한다.
        todaySolvedProblems.sort((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()));

        // 문제들을 최신순으로 정렬한 후, 최신순으로 정렬된 문제들을 RecentlyTodaySolvedProblemResponseDto로 변환한다.
        List<RecentlyTodaySolvedProblemResponseDto> results = new ArrayList<>();
        for (UserSolvedProblem userSolvedProblem : todaySolvedProblems) {
            User user = userRepository.findByBojHandle(userSolvedProblem.getBojHandle())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));

            RecentlyTodaySolvedProblemResponseDto userSolvedDto = RecentlyTodaySolvedProblemResponseDto.builder()
                    .solvedAt(userSolvedProblem.getCreatedDate())
                    .bojHandle(user.getBojHandle())
                    .notionId(user.getNotionId())
                    .profileImg(user.getProfileImg())
                    .emoji(user.getEmoji())
                    .problem(problemService.findProblem(userSolvedProblem.getProblemId()))
                    .build();
            results.add(userSolvedDto);
        }

        return results;
    }
}
