package com.randps.randomdefence.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.component.parser.BojParserImpl;
import com.randps.randomdefence.component.parser.SolvedacParserImpl;
import com.randps.randomdefence.component.query.Query;
import com.randps.randomdefence.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.domain.UserRandomStreak;
import com.randps.randomdefence.user.domain.UserRandomStreakRepository;
import com.randps.randomdefence.user.domain.UserRepository;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import com.randps.randomdefence.user.dto.UserRandomStreakResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserGrassService userGrassService;

    private final UserRandomStreakRepository userRandomStreakRepository;

    private final UserInfoService userInfoService;

    private final UserRandomStreakService userRandomStreakService;

    private final UserSolvedProblemService userSolvedProblemService;

    /*
     * 유저를 DB에 저장한다.
     */
    @Transactional
    public User save(String bojHandle, String notionId, Long manager, String emoji) throws JsonProcessingException {
        Optional<User> isExistUser = userRepository.findByBojHandle(bojHandle);
        if (isExistUser.isPresent()) {
            return isExistUser.get();
        }
        if (!(manager == 0 || manager == 1)) {
            throw new IllegalArgumentException("잘못된 파라미터가 전달되었습니다.");
        }

        User user = User.builder()
                .bojHandle(bojHandle)
                .notionId(notionId)
                .manager(manager==1?true:false)
                .warning(0)
                .profileImg("")
                .emoji(emoji)
                .tier(0)
                .totalSolved(0)
                .currentStreak(0)
                .currentRandomStreak(0)
                .team(0)
                .point(0)
                .isTodaySolved(false)
                .isTodayRandomSolved(false)
                .build();

        userRepository.save(user);

        // 유저 프로필 정보 크롤링
        userInfoService.crawlUserInfo(bojHandle);
        // 유저 랜덤 스트릭 생성
        userRandomStreakService.save(bojHandle);
        // 유저 오늘 푼 문제 크롤링
        userSolvedProblemService.crawlTodaySolvedProblem(bojHandle);
        // 전날의 랜덤 스트릭 잔디 생성 (새로운 유저 생성 시 에러 방지용)
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
        userGrassService.makeYesterdayGrass(userRandomStreak);

        return user;
    }

    /*
     * 유저를 DB에서 삭제한다.
     */
    @Transactional
    public void delete(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        userRepository.delete(user);
    }
}
