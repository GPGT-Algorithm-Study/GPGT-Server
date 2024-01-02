package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserRepository userRepository;

    private final UserRandomStreakService userRandomStreakService;

    private final UserSolvedProblemService userSolvedProblemService;

    /*
     * 유저를 DB에서 삭제한다.
     */
    @Transactional
    public void delete(String bojHandle) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        // 유저 랜덤 스트릭 & 잔디 삭제
        userRandomStreakService.deleteUserRandomStreak(bojHandle);
        // 유저 오늘 푼 문제 삭제
        userSolvedProblemService.deleteAllByBojHandle(bojHandle);
        // 유저 통계 삭제
        // 유저 JWT 토큰 삭제
        // 유저 나의 한마디 삭제
        // 유저 포인트 로그 삭제
        // 유저 경고 로그 삭제
        // 유저 아이템 삭제
        // 유저 게시글 삭제
        // 유저 댓글 삭제

        userRepository.delete(user);
    }

}
