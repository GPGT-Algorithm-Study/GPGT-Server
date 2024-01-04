package com.randps.randomdefence.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.domain.user.dto.authDto.LoginRequest;
import com.randps.randomdefence.global.component.mock.FakeParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDeleteServiceTest {

    @Test
    public void delete를_이용해_유저를_bojHandle로_삭제할_수_있다() throws JsonProcessingException {
        // given
        UserScrapingInfoDto userScrapingInfoDto = UserScrapingInfoDto.builder()
                .tier(15)
                .profileImg("https://static.solved.ac/uploads/profile/64x64/fin-picture-1665752455693.png")
                .currentStreak(252)
                .totalSolved(1067)
                .isTodaySolved(true)
                .todaySolvedProblemCount(1)
                .build();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        TestContainer testContainer = TestContainer.builder()
                .parser(new FakeParserImpl())
                .solvedacParser(new FakeSolvedacParserImpl(userScrapingInfoDto))
                .passwordEncoder(passwordEncoder)
                .build();
        UserSave userSave = UserSave.builder()
                .bojHandle("fin")
                .password("q1w2e3r4!")
                .notionId("성민")
                .manager(1L)
                .emoji("🛠️")
                .build();
        testContainer.userService.save(userSave);
        testContainer.userAuthService.login(LoginRequest.builder()
                .bojHandle("fin")
                .password("q1w2e3r4!")
                .build());
        testContainer.boolshitRepository.save(Boolshit.builder()
                .message("test")
                .bojHandle("fin")
                .build());

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();
        List<SolvedProblemDto> userSolvedProblemResults = testContainer.userSolvedProblemService.findAllUserSolvedProblem("fin");

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 프로필 정보 삭제
        assertThatThrownBy(() -> {
            testContainer.userInfoService.getInfo("fin");
        }).isInstanceOf(IllegalArgumentException.class);
        // 유저 랜덤 스트릭 삭제 & 유저 랜덤 스트릭 잔디 삭제
        assertThatThrownBy(() -> {
            testContainer.userRandomStreakService.findUserRandomStreak("fin");
        }).isInstanceOf(IllegalArgumentException.class);
        // 유저 오늘 푼 문제 삭제
        assertThat(userSolvedProblemResults.size()).isEqualTo(0);
        // 유저 통계 삭제
        assertThat(testContainer.userStatisticsRepository.findByBojHandle("fin")).isEmpty();
        // 유저 JWT 토큰 삭제
        assertThat(testContainer.refreshTokenRepository.findByBojHandle("fin")).isEmpty();
        // 유저 나의 한마디 삭제
        assertThat(testContainer.boolshitRepository.findAll().size()).isEqualTo(0);
//        // 유저 포인트 로그 삭제
//        assertThat(testContainer.pointLogRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
//        // 유저 경고 로그 삭제
//        assertThat(testContainer.warningLogRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
//        // 유저 아이템 삭제
//        assertThat(testContainer.userItemRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
//        // 유저 게시글 삭제
//        assertThat(testContainer.boardRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
//        // 유저 댓글 삭제
//        assertThat(testContainer.commentRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
        // TODO : Resolve this test
    }

    @Test
    public void delete를_이용해_존재하지_않는_유저를_삭제하면_예외를_던진다() {
        // given

        // when

        // then

    }

}
