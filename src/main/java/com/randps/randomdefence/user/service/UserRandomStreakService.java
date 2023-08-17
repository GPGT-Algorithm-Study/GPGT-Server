package com.randps.randomdefence.user.service;

import com.randps.randomdefence.log.domain.PointLogRepository;
import com.randps.randomdefence.log.service.PointLogSaveService;
import com.randps.randomdefence.problem.dto.ProblemDto;
import com.randps.randomdefence.problem.service.ProblemService;
import com.randps.randomdefence.recommendation.dto.RecommendationResponse;
import com.randps.randomdefence.recommendation.service.RecommendationService;
import com.randps.randomdefence.user.domain.*;
import com.randps.randomdefence.user.dto.SolvedProblemDto;
import com.randps.randomdefence.user.dto.UserRandomStreakResponse;
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

    private final PointLogRepository pointLogRepository;

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
    public void updateLevel(String bojHandle, String startLevel, String endLevel) {
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));

        // 유효성 검사도 넣으면 좋음
        userRandomStreak.updateLevel(startLevel, endLevel);
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
        String query = recommendationService.makeQuery(bojHandle, userRandomStreak.getStartLevel(), userRandomStreak.getEndLevel());
        RecommendationResponse recommendationResponse = recommendationService.makeRecommend(query);

        // 랜덤 스트릭 정보 갱신
        userRandomStreak.setTodayRandomProblemId(recommendationResponse.getProblemId());
        userRandomStreakRepository.save(userRandomStreak);

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
            String query = recommendationService.makeQuery(user.getBojHandle(), userRandomStreak.getStartLevel(), userRandomStreak.getEndLevel());
            RecommendationResponse recommendationResponse = recommendationService.makeRecommend(query);

            // 랜덤 스트릭의 정보 갱신
            userRandomStreak.setTodayRandomProblemId(recommendationResponse.getProblemId());
            userRandomStreakRepository.save(userRandomStreak);

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

        if (userRandomStreak.getIsTodayRandomSolved()) return true;

        for (SolvedProblemDto solvedProblemDto : solvedProblemDtos) {
            if (solvedProblemDto.getProblemId().equals(randomProblem.getProblemId())) {
                User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
                UserGrass todayUserGrass = userGrassService.findTodayUserGrass(userRandomStreak);

                // 유저의 정보 갱신
                user.increasePoint(randomProblem.getLevel() * 2); // 문제의 레벨 * 2만큼의 포인트를 지급한다.
                pointLogSaveService.savePointLog(bojHandle, randomProblem.getLevel() * 2,  randomProblem.getLevel() * 2 + " point earn by solving random problem " + randomProblem.getProblemId().toString() + " : " + "\"" + randomProblem.getTitleKo() + "\""+ " level - " + convertDifficulty(randomProblem.getLevel()), true);

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

            if (userRandomStreak.getIsTodayRandomSolved()) continue;

            for (SolvedProblemDto solvedProblemDto : solvedProblemDtos) {
                if (solvedProblemDto.getProblemId().equals(randomProblem.getProblemId())) {
                    User user = userRepository.findByBojHandle(userCur.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
                    UserGrass todayUserGrass = userGrassService.findTodayUserGrass(userRandomStreak);

                    // 유저의 정보 갱신
                    user.increasePoint(randomProblem.getLevel() * 2); // 문제의 레벨 * 2만큼의 포인트를 지급한다.
                    pointLogSaveService.savePointLog(user.getBojHandle(), randomProblem.getLevel() * 2,  randomProblem.getLevel() * 2 + " point earn by solving random problem " + randomProblem.getProblemId().toString() + " : " + "\"" + randomProblem.getTitleKo() + "\""+ " level - " + convertDifficulty(randomProblem.getLevel()), true);

                    user.increaseCurrentRandomStreak(); // 랜덤 스트릭 1 증가
                    user.checkTodayRandomSolvedOk();
                    userRepository.save(user);

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
     */
    @Transactional
    public Boolean streakCheck(String bojHandle) {
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
        UserGrass yesterday = userGrassService.findYesterdayUserGrass(userRandomStreak);
        if (!yesterday.getGrassInfo()) {
            User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

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
     */
    @Transactional
    public void streakCheckAll() {
        List<User> users = userRepository.findAll();

        for (User userCur : users) {
            UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(userCur.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
            UserGrass yesterday = userGrassService.findYesterdayUserGrass(userRandomStreak);
            if (!yesterday.getGrassInfo()) {
                User user = userRepository.findByBojHandle(userCur.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

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
