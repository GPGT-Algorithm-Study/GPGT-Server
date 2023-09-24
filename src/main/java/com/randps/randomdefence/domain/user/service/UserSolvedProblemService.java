package com.randps.randomdefence.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.event.service.EventPointService;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.user.domain.*;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.domain.user.dto.UserSolvedProblemPairDto;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.parser.BojParserImpl;
import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.randps.randomdefence.global.component.crawler.BojWebCrawler.is6AmAfter;
import static com.randps.randomdefence.global.component.parser.BojParserImpl.convertDifficulty;

@RequiredArgsConstructor
@Service
public class UserSolvedProblemService {

    private final UserSolvedProblemRepository userSolvedProblemRepository;

    private final UserRandomStreakRepository userRandomStreakRepository;

    private final ProblemService problemService;

    private final PointLogSaveService pointLogSaveService;

    private final TeamService teamService;

    private final UserRepository userRepository;

    private final BojParserImpl bojParser;

    private final UserStatisticsService userStatisticsService;

    private final UserAlreadySolvedService userAlreadySolvedService;

    private final EventPointService eventPointService;

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
            solvedProblemDto.setPoint(problemDto.getLevel());
            solvedProblemDto.setLanguage(problem.getLanguage());
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
                solvedProblemDto.setLanguage(problem.getLanguage());
                solvedProblemDto.setPoint(problemDto.getLevel());
                solvedProblems.add(solvedProblemDto);
            }
        }

        return solvedProblems;
    }

    /*
     * 오늘 모든 유저가 푼 모든 문제의 정보를 가져온다.
     */
    @Transactional
    public List<UserSolvedProblemPairDto> findAllTodayUserSolvedProblemAll() {
        // 오늘의 기준을 만든다.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDateTime;
        if (is6AmAfter(now.getHour()))
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        else {
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }

        // 모든 유저 조회
        List<User> users = userRepository.findAll();
        List<UserSolvedProblemPairDto> userSolvedProblemPairDtos = new ArrayList<>();

        for (User user : users) {
            // 데이터를 DB에서 가져온다.
            List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(user.getBojHandle());
            List<SolvedProblemDto> solvedProblems = new ArrayList<>();

            // DB문제의 푼 날짜를 비교해서 오늘 푼 문제만 넣는다.
            for (UserSolvedProblem problem : userSolvedProblems) {
                LocalDateTime target = LocalDateTime.of(Integer.valueOf(problem.getDateTime().substring(0, 4)), Integer.valueOf(problem.getDateTime().substring(5, 7)), Integer.valueOf(problem.getDateTime().substring(8, 10)), Integer.valueOf(problem.getDateTime().substring(11, 13)), Integer.valueOf(problem.getDateTime().substring(14, 16)), Integer.valueOf(problem.getDateTime().substring(18)), 0);

                if (startOfDateTime.isBefore(target)) {
                    SolvedProblemDto solvedProblemDto = problem.toDto();
                    ProblemDto problemDto = problemService.findProblem(solvedProblemDto.getProblemId());
                    solvedProblemDto.setTier(problemDto.getLevel());
                    solvedProblemDto.setTags(problemDto.getTags());
                    solvedProblemDto.setPoint(problemDto.getPoint());
                    solvedProblemDto.setLanguage(problem.getLanguage());
                    solvedProblems.add(solvedProblemDto);
                }
            }

            userSolvedProblemPairDtos.add(UserSolvedProblemPairDto.builder()
                    .bojHandle(user.getBojHandle())
                    .solvedProblemList(solvedProblems)
                    .build());
        }

        return userSolvedProblemPairDtos;
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
                    .language(pair.getLanguage())
                    .build();
            // 중복 제거 로직
            Boolean isAlreadyExist = false;
            for (UserSolvedProblem alreadySolvedProblem : userSolvedProblems){
                if (alreadySolvedProblem.getProblemId().equals(userSolvedProblem.getProblemId())) {
                    isAlreadyExist = true;
                    break;
                }
            }
            // 중복이 없다면 저장한다.
            if (!isAlreadyExist && !userAlreadySolvedService.isSolved(bojHandle, userSolvedProblem.getProblemId())) {
                // 문제의 포인트만큼 유저의 포인트를 추가한다.
                ProblemDto pb = problemService.findProblem(userSolvedProblem.getProblemId());
                User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
                UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스트릭입니다."));
                // 랜덤 스트릭 문제라면 따로 포인트를 부여한다.
                if (!userRandomStreak.getTodayRandomProblemId().equals(pb.getProblemId())) {
                    // 일반 문제의 포인트 부여
                    user.increasePoint(pb.getPoint());
                    pointLogSaveService.savePointLog(user.getBojHandle(), pb.getPoint(),  pb.getPoint() + " points are earned by solving problem " + pb.getProblemId().toString() + " : " + "\"" + pb.getTitleKo() + "\""+ " level - " + convertDifficulty(pb.getLevel()), true);

                    // 이벤트를 적용한다
                    eventPointService.applyEventPoint(user.getBojHandle(), pb.getPoint());

                    // 팀의 점수를 올린다. (일반 문제)
                    teamService.increaseTeamScore(user.getTeam(), pb.getPoint());
                    // 유저 통계를 반영한다. (일반 문제)
                    userStatisticsService.updateByDto(user.getBojHandle(), pb, pb.getPoint());
                }

                userSolvedProblems.add(userSolvedProblem);
            }
        }

//        // 중복을 제거하고 저장한다.
//        userSolvedProblems.stream().distinct();
        userSolvedProblemRepository.saveAll(userSolvedProblems);
    }

    /*
     * 모든 유저가 오늘 푼 문제를 크롤링 후, DB에 저장한다.
     */
    @Transactional
    public void crawlTodaySolvedProblemAll() throws JsonProcessingException {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<Object> problems = bojParser.getSolvedProblemList(user.getBojHandle());
            // 중복 제거를 위해 기존의 푼 문제 목록을 가져온다.
            List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(user.getBojHandle());

            // 모든 스크래핑 한 데이터를 푼 문제 목록에 추가한다.
            for (Object problem : problems) {
                BojProblemPair pair = (BojProblemPair) problem;
                UserSolvedProblem userSolvedProblem = UserSolvedProblem.builder()
                        .bojHandle(user.getBojHandle())
                        .problemId(pair.getProblemId())
                        .title(pair.getTitle())
                        .dateTime(pair.getDateTime())
                        .language(pair.getLanguage())
                        .build();
                // 중복 제거 로직
                Boolean isAlreadyExist = false;
                for (UserSolvedProblem alreadySolvedProblem : userSolvedProblems) {
                    if (alreadySolvedProblem.getProblemId().equals(userSolvedProblem.getProblemId())) {
                        isAlreadyExist = true;
                        break;
                    }
                }
                // 중복이 없다면 저장한다.
                if (!isAlreadyExist && !userAlreadySolvedService.isSolved(user.getBojHandle(), userSolvedProblem.getProblemId())) {
                    // 문제의 포인트만큼 유저의 포인트를 추가한다.
                    ProblemDto pb = problemService.findProblem(userSolvedProblem.getProblemId());
                    UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(user.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스트릭입니다."));

                    // 랜덤 스트릭 문제라면 따로 포인트를 부여한다.
                    if (!userRandomStreak.getTodayRandomProblemId().equals(pb.getProblemId())) {
                        // 일반 문제의 포인트 부여
                        user.increasePoint(pb.getPoint());
                        pointLogSaveService.savePointLog(user.getBojHandle(), pb.getPoint(),  pb.getPoint() + " points are earned by solving problem " + pb.getProblemId().toString() + " : " + "\"" + pb.getTitleKo() + "\""+ " level - " + convertDifficulty(pb.getLevel()), true);

                        // 이벤트를 적용한다
                        eventPointService.applyEventPoint(user.getBojHandle(), pb.getPoint());

                        // 팀의 점수를 올린다. (일반 문제)
                        teamService.increaseTeamScore(user.getTeam(), pb.getPoint());
                        // 유저 통계를 반영한다. (일반 문제)
                        userStatisticsService.updateByDto(user.getBojHandle(), pb, pb.getPoint());
                    }

                    userSolvedProblems.add(userSolvedProblem);
                }
            }

//        // 중복을 제거하고 저장한다.
//        userSolvedProblems.stream().distinct();
            userSolvedProblemRepository.saveAll(userSolvedProblems);
        }
    }

    /*
     * 오늘 유저가 풀었는지 여부를 반환한다.
     */
    @Transactional
    public Boolean isTodaySolved(String bojHandle) {
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

        // DB문제의 푼 날짜를 비교해서 오늘 푼 문제가 한개라도 있다면 true를 반환한다.
        for (UserSolvedProblem problem : userSolvedProblems) {
            LocalDateTime target = LocalDateTime.of(Integer.valueOf(problem.getDateTime().substring(0,4)), Integer.valueOf(problem.getDateTime().substring(5,7)), Integer.valueOf(problem.getDateTime().substring(8,10)), Integer.valueOf(problem.getDateTime().substring(11,13)), Integer.valueOf(problem.getDateTime().substring(14,16)), Integer.valueOf(problem.getDateTime().substring(18)), 0);

            if (startOfDateTime.isBefore(target)) {
                return true;
            }
        }

        return false;
    }

    /*
     * 어제 유저가 문제를 풀었는지 여부를 반환한다.
     */
    @Transactional
    public Boolean isYesterdaySolved(String bojHandle) {
        // 어제의 기준을 만든다.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDateTime;
        LocalDateTime endOfDateTime;
        if (is6AmAfter(now.getHour())) {
            endOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }
        else {
            now = now.minusDays(1);
            endOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }

        // 데이터를 DB에서 가져온다.
        List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(bojHandle);

        // DB문제의 푼 날짜를 비교해서 어제 푼 문제가 한개라도 있다면 true를 반환한다.
        for (UserSolvedProblem problem : userSolvedProblems) {
            LocalDateTime target = LocalDateTime.of(Integer.valueOf(problem.getDateTime().substring(0,4)), Integer.valueOf(problem.getDateTime().substring(5,7)), Integer.valueOf(problem.getDateTime().substring(8,10)), Integer.valueOf(problem.getDateTime().substring(11,13)), Integer.valueOf(problem.getDateTime().substring(14,16)), Integer.valueOf(problem.getDateTime().substring(18)), 0);
            if (target.isAfter(startOfDateTime) && target.isBefore(endOfDateTime)) {
                return true;
            }
        }
        return false;
    }

    /*
     * 오늘 몇 문제 풀었는지 개수를 반환한다.
     */
    @Transactional
    public Integer getTodaySolvedProblemCount(String bojHandle) {
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

        Integer cnt = 0;
        // DB문제의 푼 날짜를 비교해서 오늘 푼 문제의 개수를 새고 반환한다.
        for (UserSolvedProblem problem : userSolvedProblems) {
            LocalDateTime target = LocalDateTime.of(Integer.valueOf(problem.getDateTime().substring(0,4)), Integer.valueOf(problem.getDateTime().substring(5,7)), Integer.valueOf(problem.getDateTime().substring(8,10)), Integer.valueOf(problem.getDateTime().substring(11,13)), Integer.valueOf(problem.getDateTime().substring(14,16)), Integer.valueOf(problem.getDateTime().substring(18)), 0);

            if (startOfDateTime.isBefore(target)) {
                cnt++;
            }
        }

        return cnt;
    }

    /*
     * 어제 몇 문제 풀었는지 개수를 반환한다.
     */
    @Transactional
    public Integer getYesterdaySolvedProblemCount(String bojHandle) {
        // 어제의 기준을 만든다.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDateTime;
        LocalDateTime endOfDateTime;
        if (is6AmAfter(now.getHour())) {
            endOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }
        else {
            now = now.minusDays(1);
            endOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
            now = now.minusDays(1);
            startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0, 0);
        }

        // 데이터를 DB에서 가져온다.
        List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(bojHandle);

        Integer cnt = 0;
        // DB문제의 푼 날짜를 비교해서 어제 푼 문제의 개수를 새고 반환한다.
        for (UserSolvedProblem problem : userSolvedProblems) {
            LocalDateTime target = LocalDateTime.of(Integer.valueOf(problem.getDateTime().substring(0,4)), Integer.valueOf(problem.getDateTime().substring(5,7)), Integer.valueOf(problem.getDateTime().substring(8,10)), Integer.valueOf(problem.getDateTime().substring(11,13)), Integer.valueOf(problem.getDateTime().substring(14,16)), Integer.valueOf(problem.getDateTime().substring(18)), 0);

            if (target.isAfter(startOfDateTime) && target.isBefore(endOfDateTime)) {
                cnt++;
            }
        }

        return cnt;
    }
}
