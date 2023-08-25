package com.randps.randomdefence.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.domain.UserRandomStreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

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
    public User save(String bojHandle, String password, String notionId, Long manager, String emoji) throws JsonProcessingException {
        Optional<User> isExistUser = userRepository.findByBojHandle(bojHandle);
        if (isExistUser.isPresent()) {
            throw new EntityExistsException("이미 존재하는 유저는 생성할 수 없습니다.");
        }
        if (!(manager == 0 || manager == 1)) {
            throw new IllegalArgumentException("잘못된 파라미터가 전달되었습니다.");
        }

        User user = User.builder()
                .bojHandle(bojHandle)
                .notionId(notionId)
                .password(passwordEncoder.encode(password)) // encoder로 암호화 후 넣기
                .roles(manager==1?"ROLE_USER,ROLE_ADMIN":"ROLE_USER") // 유저의 권한 설정
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
        // 오늘의 랜덤 스트릭 잔디 생성
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
        userGrassService.makeTodayGrass(userRandomStreak);
        // 전날의 랜덤 스트릭 잔디 생성 (새로운 유저 생성 시 에러 방지용)
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
