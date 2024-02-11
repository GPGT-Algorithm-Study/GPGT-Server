package com.randps.randomdefence.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.dto.UserLastLoginLogDto;
import com.randps.randomdefence.domain.user.dto.UserMentionDto;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.domain.user.service.port.UserRandomStreakRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserGrassService userGrassService;

    private final UserRandomStreakRepository userRandomStreakRepository;

    private final UserInfoService userInfoService;

    private final UserRandomStreakService userRandomStreakService;

    private final UserSolvedProblemService userSolvedProblemService;

    private static HashMap<String, Boolean> userSaveProcessSet = new HashMap<>();

    /*
     * 유저를 DB에 저장한다.
     */
    @Transactional
    public User save(UserSave userSave) throws JsonProcessingException {
        Optional<User> existUser = userRepository.findByBojHandle(userSave.getBojHandle());
        synchronized (this) {
            if (existUser.isPresent() || userSaveProcessSet.get(userSave.getBojHandle()) != null) {
                throw new EntityExistsException("이미 존재하는 유저는 생성할 수 없습니다.");
            } else {
                // Process HashMap에 추가
                userSaveProcessSet.put(userSave.getBojHandle(), true);
            }
        }
        if (!(userSave.getManager() == 0 || userSave.getManager() == 1)) {
            throw new IllegalArgumentException("잘못된 파라미터가 전달되었습니다.");
        }

        User user = User.builder()
                .bojHandle(userSave.getBojHandle())
                .notionId(userSave.getNotionId())
                .password(passwordEncoder.encode(userSave.getPassword())) // encoder로 암호화 후 넣기
                .roles(userSave.getManager()==1?"ROLE_USER,ROLE_ADMIN":"ROLE_USER") // 유저의 권한 설정
                .manager(userSave.getManager() == 1)
                .warning(0)
                .profileImg("")
                .emoji(userSave.getEmoji())
                .tier(0)
                .totalSolved(0)
                .currentStreak(0)
                .currentRandomStreak(0)
                .team(0)
                .point(0)
                .isTodaySolved(false)
                .isYesterdaySolved(false)
                .isTodayRandomSolved(false)
                .build();

        user = userRepository.save(user);

        // 유저 프로필 정보 크롤링
        userInfoService.crawlUserInfo(userSave.getBojHandle());
        // 유저 랜덤 스트릭 생성
        userRandomStreakService.save(userSave.getBojHandle());
        // 유저 오늘 푼 문제 크롤링
        userSolvedProblemService.crawlTodaySolvedProblem(userSave.getBojHandle());
        // 유저 오늘 문제 풀었는지 여부 크롤링
        userInfoService.updateAllUserInfo();
        // 오늘의 랜덤 스트릭 잔디 생성
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(userSave.getBojHandle()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저의 스트릭입니다."));
        userGrassService.makeTodayGrass(userRandomStreak);
        // 전날의 랜덤 스트릭 잔디 생성 (새로운 유저 생성 시 에러 방지용)
        userGrassService.makeYesterdayGrass(userRandomStreak);

        // Process HashMap에서 삭제
        userSaveProcessSet.remove(userSave.getBojHandle());
        return user;
    }

    /**
     * 모든 유저의 마지막 로그인 기록을 조회한다.
     */
    public List<UserLastLoginLogDto> findAllLastLoginLog() {
        return userRepository.findAllLastLoginDto();
    }

    /**
     * Mention을 위해 모든 유저의 NotionId를 조회한다.
     */
    public List<UserMentionDto> findAllMentionDto() {
        return userRepository.findAllUserMentionDto();
    }
}
