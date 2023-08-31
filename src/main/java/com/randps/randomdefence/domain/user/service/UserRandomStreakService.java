package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.item.service.RandomStreakFreezeItemUseServiceImpl;
import com.randps.randomdefence.domain.log.domain.PointLogRepository;
import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.recommendation.dto.RecommendationResponse;
import com.randps.randomdefence.domain.recommendation.service.RecommendationService;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.user.domain.*;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.domain.user.dto.UserRandomStreakResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.randps.randomdefence.global.component.parser.BojParserImpl.convertDifficulty;

@RequiredArgsConstructor
@Service
public class UserRandomStreakService {

    private final UserRandomStreakRepository userRandomStreakRepository;

    private final RecommendationService recommendationService;

    private final UserSolvedProblemService userSolvedProblemService;

    private final ProblemService problemService;

    private final UserRepository userRepository;

    private final UserGrassRepository userGrassRepository;

    private final UserGrassService userGrassService;

    private final PointLogSaveService pointLogSaveService;

    private final UserStatisticsService userStatisticsService;

    private final TeamService teamService;

    private final RandomStreakFreezeItemUseServiceImpl randomStreakFreezeItemUseService;

    /*
     * 유저 랜덤 스트릭 생성하기 (유저 생성 시 사용)
     */
    @Transactional
    public void save(String bojHandle) {
        UserRandomStreak userRandomStreak = UserRandomStreak.builder()
                .bojHandle(bojHandle)
                .startLevel("") // 초기화 시 빈 문자열(비활성)
                .endLevel("") // 초기화 시 빈 문자열(비활성)
                .todayRandomProblemId(0) // 문제가 없을 시 0번
                .isTodayRandomSolved(false)
                .currentRandomStreak(0)
                .maxRandomStreak(0)
                .build();
        userRandomStreakRepository.save(userRandomStreak);
    }

    /*
     * 유저 랜덤 스트릭 문제 추천 범위 업데이트 (문제 범위 바꿀 시 사용 (빈 문자열을 넣을 시 스트릭 비활성))
     */
    @Transactional
    public Boolean updateLevel(String bojHandle, String startLevel, String endLevel, Boolean isKo) {
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));

        // 풀 수 있는 문제가 존재하는지 확인한다.
        String query = recommendationService.makeQuery(bojHandle, startLevel, endLevel, isKo);
        RecommendationResponse recommendationResponse = recommendationService.makeRecommend(query);

        // 추천된 문제가 없는경우 실패를 반환
        if (recommendationResponse.getProblemId() == null) {
            return false;
        }

        // 유효성 검사도 넣으면 좋음
        Boolean isSetup = userRandomStreak.updateLevel(startLevel, endLevel, isKo);

        // 처음으로 세팅했다면 문제를 추천해준다.
        if (isSetup) {
            makeUpUserRandomProblem(bojHandle);
        }

        // 성공적으로 변경했음을 반환
        return true;
    }

    /*
     * 특정 유저의 랜덤 스트릭 정보를 불러온다. (문제를 문제의 아이디만 가진 형태)
     */
    @Transactional
    public UserRandomStreak findUserRandomStreak(String bojHandle) {
        return userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
    }

    /*
     * 특정 유저의 랜덤 스트릭 정보를 불러온다. (문제를 문제의 모든 정보를 보여주는 형태)
     */
    @Transactional
    public UserRandomStreakResponse findUserRandomStreakToResponseForm(String bojHandle) {
        UserRandomStreak userRandomStreak =  userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
        ProblemDto problemDto = problemService.findProblem(userRandomStreak.getTodayRandomProblemId());

        // 랜덤 문제의 문제의 획득 포인트 = 레벨 * 2
        problemDto.toDoublePoint();

        return new UserRandomStreakResponse(userRandomStreak.toDto(), problemDto);
    }

    /*
     * 모든 유저의 랜덤 스트릭 정보를 불러온다.
     */
    @Transactional
    public List<UserRandomStreakResponse> findAllUserRandomStreak() {
        List<UserRandomStreak> userRandomStreaks = userRandomStreakRepository.findAll();
        List<UserRandomStreakResponse> userRandomStreakResponses = new ArrayList<>();

        for (UserRandomStreak userRandomStreak : userRandomStreaks) {
            ProblemDto problemDto = problemService.findProblem(userRandomStreak.getTodayRandomProblemId());

            // 랜덤 문제의 문제의 획득 포인트 = 레벨 * 2
            problemDto.toDoublePoint();

            userRandomStreakResponses.add(new UserRandomStreakResponse(userRandomStreak.toDto(), problemDto));
        }

        return userRandomStreakResponses;
    }

    /*
     * 특정 유저의 랜덤 문제를 1문제를 뽑아 저장한다.
     */
    @Transactional
    public RecommendationResponse makeUpUserRandomProblem(String bojHandle) {
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));

        // 만약 시작과 끝이 빈 문자열이라면 애초에 이 함수를 호출해서는 안됨.
        // 랜덤 문제 고르기
        String query = recommendationService.makeQuery(bojHandle, userRandomStreak.getStartLevel(), userRandomStreak.getEndLevel(), userRandomStreak.getIsKo());
        RecommendationResponse recommendationResponse = recommendationService.makeRecommend(query);

        // 추천된 문제가 없는경우
        if (recommendationResponse.getProblemId() == null) {
            // 랜덤 스트릭 범위를 초기화 한다.
            userRandomStreak.updateLevel("", "", false);

            // 랜덤 스트릭 정보 갱신 (0번 문제로 지정)
            userRandomStreak.setTodayRandomProblemId(0);
            userRandomStreakRepository.save(userRandomStreak);
        }
        // 추천된 문제가 있는 경우
        else {
            // 랜덤 스트릭 정보 갱신
            userRandomStreak.setTodayRandomProblemId(recommendationResponse.getProblemId());
            userRandomStreakRepository.save(userRandomStreak);
        }

        // 잔디의 정보 갱신
        UserGrass todayUserGrass = userGrassService.findTodayUserGrass(userRandomStreak);
        todayUserGrass.setProblemId(recommendationResponse.getProblemId());
        todayUserGrass.infoCheckNo();
        userGrassRepository.save(todayUserGrass);

        // 유저의 정보 갱신
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.checkTodayRandomSolvedNo();
        userRepository.save(user);

        return recommendationResponse;
    }

    /*
     * 모든 유저의 랜덤 문제를 1문제를 뽑아 저장한다.
     */
    @Transactional
    public void makeUpUserRandomProblemAll() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(user.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));

            if (userRandomStreak.getStartLevel().isBlank() || userRandomStreak.getEndLevel().isBlank()) continue;
            // 랜덤 문제 고르기
            String query = recommendationService.makeQuery(user.getBojHandle(), userRandomStreak.getStartLevel(), userRandomStreak.getEndLevel(), userRandomStreak.getIsKo());
            RecommendationResponse recommendationResponse = recommendationService.makeRecommend(query);

            // 추천된 문제가 없는경우
            if (recommendationResponse.getProblemId() == null) {
                // 랜덤 스트릭 범위를 초기화 한다.
                userRandomStreak.updateLevel("", "", false);

                // 랜덤 스트릭 정보 갱신 (0번 문제로 지정)
                userRandomStreak.setTodayRandomProblemId(0);
                userRandomStreakRepository.save(userRandomStreak);
            }
            // 추천된 문제가 있는 경우
            else {
                // 랜덤 스트릭 정보 갱신
                userRandomStreak.setTodayRandomProblemId(recommendationResponse.getProblemId());
                userRandomStreakRepository.save(userRandomStreak);
            }

            // 잔디의 정보 갱신
            UserGrass todayUserGrass = userGrassService.findTodayUserGrass(userRandomStreak);
            todayUserGrass.setProblemId(recommendationResponse.getProblemId());
            todayUserGrass.infoCheckNo();
            userGrassRepository.save(todayUserGrass);

            // 유저의 정보 갱신
            user.checkTodayRandomSolvedNo();
            userRepository.save(user);
        }
    }

    /*
     * 유저가 랜덤 문제를 풀었다면 체크한다.
     */
    @Transactional
    public Boolean solvedCheck(String bojHandle) {
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
        ProblemDto randomProblem = problemService.findProblem(userRandomStreak.getTodayRandomProblemId());
        List<SolvedProblemDto> solvedProblemDtos =  userSolvedProblemService.findAllTodayUserSolvedProblem(bojHandle);
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        UserGrass todayUserGrass = userGrassService.findTodayUserGrass(userRandomStreak);

        // 유저가 오늘 문제를 풀었다면 넘어간다.
        if (userRandomStreak.getIsTodayRandomSolved()) return true;

        for (SolvedProblemDto solvedProblemDto : solvedProblemDtos) {
            if (solvedProblemDto.getProblemId().equals(randomProblem.getProblemId())) {

                // 유저의 정보 갱신
                user.increasePoint(randomProblem.getLevel() * 2); // 문제의 레벨 * 2만큼의 포인트를 지급한다.
                pointLogSaveService.savePointLog(bojHandle, randomProblem.getLevel() * 2,  randomProblem.getLevel() * 2 + " points are earned by solving random problem " + randomProblem.getProblemId().toString() + " : " + "\"" + randomProblem.getTitleKo() + "\""+ " level - " + convertDifficulty(randomProblem.getLevel()), true);

                // 팀의 점수를 올린다. (랜덤 문제)
                teamService.increaseTeamScore(user.getTeam(), randomProblem.getLevel() * 2);
                // 유저 통계를 반영한다. (랜덤 문제)
                userStatisticsService.updateByDto(user.getBojHandle(), randomProblem, randomProblem.getLevel() * 2);

                user.increaseCurrentRandomStreak(); // 랜덤 스트릭 1 증가
                user.checkTodayRandomSolvedOk();
                userRepository.save(user);

                // 잔디 정보 갱신
                todayUserGrass.infoCheckOk();
                userGrassRepository.save(todayUserGrass);

                // 랜덤 스트릭 정보 갱신
                userRandomStreak.solvedCheckOk();
                userRandomStreakRepository.save(userRandomStreak);
                return true;
            }
        }

        return false;
    }

    /*
     * 모든 유저에 대해 유저가 랜덤 문제를 풀었다면 체크한다.
     */
    @Transactional
    public void solvedCheckAll() {
        List<User> users = userRepository.findAll();

        for (User userCur : users) {
            UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(userCur.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
            ProblemDto randomProblem = problemService.findProblem(userRandomStreak.getTodayRandomProblemId());
            List<SolvedProblemDto> solvedProblemDtos = userSolvedProblemService.findAllTodayUserSolvedProblem(userCur.getBojHandle());

            // 유저가 오늘 문제를 풀었다면 넘어간다.
            if (userRandomStreak.getIsTodayRandomSolved()) continue;

            for (SolvedProblemDto solvedProblemDto : solvedProblemDtos) {
                if (solvedProblemDto.getProblemId().equals(randomProblem.getProblemId())) {
                    UserGrass todayUserGrass = userGrassService.findTodayUserGrass(userRandomStreak);

                    // 유저의 정보 갱신
                    userCur.increasePoint(randomProblem.getLevel() * 2); // 문제의 레벨 * 2만큼의 포인트를 지급한다.
                    pointLogSaveService.savePointLog(userCur.getBojHandle(), randomProblem.getLevel() * 2,  randomProblem.getLevel() * 2 + " points are earned by solving random problem " + randomProblem.getProblemId().toString() + " : " + "\"" + randomProblem.getTitleKo() + "\""+ " level - " + convertDifficulty(randomProblem.getLevel()), true);

                    // 팀의 점수를 올린다. (랜덤 문제)
                    teamService.increaseTeamScore(userCur.getTeam(), randomProblem.getLevel() * 2);
                    // 유저 통계를 반영한다. (랜덤 문제)
                    userStatisticsService.updateByDto(userCur.getBojHandle(), randomProblem, randomProblem.getLevel() * 2);

                    userCur.increaseCurrentRandomStreak(); // 랜덤 스트릭 1 증가
                    userCur.checkTodayRandomSolvedOk();
                    userRepository.save(userCur);

                    // 잔디 정보 갱신
                    todayUserGrass.infoCheckOk();
                    userGrassRepository.save(todayUserGrass);

                    // 랜덤 스트릭 정보 갱신
                    userRandomStreak.solvedCheckOk();
                    userRandomStreakRepository.save(userRandomStreak);
                }
            }
        }
    }

    /*
     * 유저의 전일 문제가 풀리지 않았다면 스트릭을 끊는다.
     * 문제를 풀지 않았고, 스트릭 프리즈가 있다면 스트릭 프리즈를 사용한다.
     */
    @Transactional
    public Boolean streakCheck(String bojHandle) {
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
        UserGrass yesterday = userGrassService.findYesterdayUserGrass(userRandomStreak);
        if (!yesterday.getGrassInfo()) {
            User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

            // 스트릭 프리즈가 있다면 사용한다.
            if (randomStreakFreezeItemUseService.isExist(user)) {
                randomStreakFreezeItemUseService.useItem(user, 3L);
                // 스트릭 프리즈를 사용했으므로 넘어간다.
                return true;
            }

            // 유저 정보 갱신
            user.checkTodayRandomSolvedNo();
            user.resetCurrentRandomStreak();
            userRepository.save(user);

            // 랜덤 스트릭 정보 갱신
            userRandomStreak.resetCurrentStreak();
            userRandomStreakRepository.save(userRandomStreak);

            return false;
        }
        return true;
    }

    /*
     * 모든 유저에 대해 유저의 전일 문제가 풀리지 않았다면 스트릭을 끊는다.
     * 문제를 풀지 않았고, 스트릭 프리즈가 있다면 스트릭 프리즈를 사용한다.
     */
    @Transactional
    public void streakCheckAll() {
        List<User> users = userRepository.findAll();

        for (User userCur : users) {
            UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(userCur.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
            UserGrass yesterday = userGrassService.findYesterdayUserGrass(userRandomStreak);
            if (!yesterday.getGrassInfo()) {
                User user = userRepository.findByBojHandle(userCur.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

                // 스트릭 프리즈가 있다면 사용한다.
                if (randomStreakFreezeItemUseService.isExist(user)) {
                    randomStreakFreezeItemUseService.useItem(user, 3L);
                    // 스트릭 프리즈를 사용했으므로 넘어간다.
                    continue;
                }

                // 유저 정보 갱신
                user.checkTodayRandomSolvedNo();
                user.resetCurrentRandomStreak();
                userRepository.save(user);

                // 랜덤 스트릭 정보 갱신
                userRandomStreak.resetCurrentStreak();
                userRandomStreakRepository.save(userRandomStreak);
            }
        }
    }
}
