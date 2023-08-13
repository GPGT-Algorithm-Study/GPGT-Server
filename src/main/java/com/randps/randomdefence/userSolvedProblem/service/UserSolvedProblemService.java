package com.randps.randomdefence.userSolvedProblem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.component.crawler.BojWebCrawler;
import com.randps.randomdefence.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.component.parser.BojParserImpl;
import com.randps.randomdefence.problem.dto.ProblemDto;
import com.randps.randomdefence.problem.service.ProblemService;
import com.randps.randomdefence.userSolvedProblem.domain.UserSolvedProblem;
import com.randps.randomdefence.userSolvedProblem.domain.UserSolvedProblemRepository;
import com.randps.randomdefence.userSolvedProblem.dto.SolvedProblemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.randps.randomdefence.component.crawler.BojWebCrawler.is6AmAfter;

@RequiredArgsConstructor
@Service
public class UserSolvedProblemService {

    private final UserSolvedProblemRepository userSolvedProblemRepository;

    private final ProblemService problemService;


    private final BojParserImpl bojParser;

    /*
     * 유저가 그동안 푼 모든 문제의 정보를 가져온다.
     */
    @Transactional
    public List<SolvedProblemDto> findAllUserSolvedProblem(String bojHandle) {
        List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(bojHandle);
        List<SolvedProblemDto> solvedProblems = new ArrayList<SolvedProblemDto>();

        for (UserSolvedProblem problem : userSolvedProblems) {
            SolvedProblemDto solvedProblemDto = problem.toDto();
            ProblemDto problemDto = problemService.findProblem(solvedProblemDto.getProblemId());
            solvedProblemDto.setTier(problemDto.getLevel());
            solvedProblemDto.setTags(problemDto.getTags());
            solvedProblems.add(solvedProblemDto);
        }

        return solvedProblems;
    }

    /*
     * 오늘 유저가 푼 모든 문제의 정보를 가져온다.
     */
    @Transactional
    public List<SolvedProblemDto> findAllTodayUserSolvedProblem(String bojHandle) {
        // 오늘의 기준을 만든다.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDateTime;
        if (is6AmAfter(now.getHour()))
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        else {
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }

        // 데이터를 DB에서 가져온다.
        List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(bojHandle);
        List<SolvedProblemDto> solvedProblems = new ArrayList<>();

        // DB문제의 푼 날짜를 비교해서 오늘 푼 문제만 넣는다.
        for (UserSolvedProblem problem : userSolvedProblems) {
            LocalDateTime target = LocalDateTime.of(Integer.valueOf(problem.getDateTime().substring(0,4)), Integer.valueOf(problem.getDateTime().substring(5,7)), Integer.valueOf(problem.getDateTime().substring(8,10)), Integer.valueOf(problem.getDateTime().substring(11,13)), Integer.valueOf(problem.getDateTime().substring(14,16)), Integer.valueOf(problem.getDateTime().substring(18)), 0);

            if (startOfDateTime.isBefore(target)) {
                SolvedProblemDto solvedProblemDto = problem.toDto();
                ProblemDto problemDto = problemService.findProblem(solvedProblemDto.getProblemId());
                solvedProblemDto.setTier(problemDto.getLevel());
                solvedProblemDto.setTags(problemDto.getTags());
                solvedProblems.add(solvedProblemDto);
            }
        }

        return solvedProblems;
    }

    /*
     * 유저가 오늘 푼 문제를 크롤링 후, DB에 저장한다.
     */
    @Transactional
    public void crawlTodaySolvedProblem(String bojHandle) throws JsonProcessingException {
        List<Object> problems = bojParser.getSolvedProblemList(bojHandle);
        // 중복 제거를 위해 기존의 푼 문제 목록을 가져온다.
        List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(bojHandle);

        // 모든 스크래핑 한 데이터를 푼 문제 목록에 추가한다.
        for (Object problem : problems) {
            BojProblemPair pair = (BojProblemPair) problem;
            UserSolvedProblem userSolvedProblem = UserSolvedProblem.builder()
                    .bojHandle(bojHandle)
                    .problemId(pair.getProblemId())
                    .title(pair.getTitle())
                    .dateTime(pair.getDateTime())
                    .build();
            if (!userSolvedProblems.contains(userSolvedProblem))
                userSolvedProblems.add(userSolvedProblem);
        }

//        // 중복을 제거하고 저장한다.
//        userSolvedProblems.stream().distinct();
        userSolvedProblemRepository.saveAll(userSolvedProblems);
    }

}
