package com.randps.randomdefence.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.mock.FakeParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceTest {

    @Test
    public void save를_이용하여_유저를_생성할_수_있다() throws JsonProcessingException {
        // given
        UserScrapingInfoDto userScrapingInfoDto = UserScrapingInfoDto.builder()
                .tier(15)
                .profileImg("https://static.solved.ac/uploads/profile/64x64/fin-picture-1665752455693.png")
                .currentStreak(252)
                .totalSolved(1067)
                .isTodaySolved(false)
                .todaySolvedProblemCount(0)
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

        // when
        User userResult = testContainer.userService.save(userSave);
        UserRandomStreak userRandomStreakResult = testContainer.userRandomStreakService.findUserRandomStreak("fin");
        UserInfoResponse userInfoResponseResult = testContainer.userInfoService.getInfo("fin");

        // then
        assertThat(userResult.getId()).isEqualTo(1L);
        assertThat(userResult.getBojHandle()).isEqualTo("fin");
        assertThat(passwordEncoder.matches("q1w2e3r4!", userResult.getPassword())).isTrue();
        assertThat(userResult.getNotionId()).isEqualTo("성민");
        assertThat(userResult.getManager()).isTrue();
        assertThat(userResult.getEmoji()).isEqualTo("🛠️");

        assertThat(userRandomStreakResult.getId()).isEqualTo(1);
        assertThat(userRandomStreakResult.getBojHandle()).isEqualTo("fin");
        assertThat(userRandomStreakResult.getMaxRandomStreak()).isEqualTo(0);
        assertThat(userRandomStreakResult.getCurrentRandomStreak()).isEqualTo(0);
        assertThat(userRandomStreakResult.getTodayRandomProblemId()).isEqualTo(0);
        assertThat(userRandomStreakResult.getIsTodayRandomSolved()).isFalse();
        assertThat(userRandomStreakResult.getIsKo()).isTrue();
        assertThat(userRandomStreakResult.getStartLevel()).isEqualTo("");
        assertThat(userRandomStreakResult.getEndLevel()).isEqualTo("");

        assertThat(userInfoResponseResult.getBojHandle()).isEqualTo("fin");
        assertThat(userInfoResponseResult.getNotionId()).isEqualTo("성민");
        assertThat(userInfoResponseResult.getManager()).isTrue();
        assertThat(userInfoResponseResult.getWarning()).isEqualTo(0);
        assertThat(userInfoResponseResult.getProfileImg()).isEqualTo("https://static.solved.ac/uploads/profile/64x64/fin-picture-1665752455693.png");
        assertThat(userInfoResponseResult.getEmoji()).isEqualTo("🛠️");
        assertThat(userInfoResponseResult.getTier()).isEqualTo(15);
        assertThat(userInfoResponseResult.getTotalSolved()).isEqualTo(1067);
        assertThat(userInfoResponseResult.getCurrentStreak()).isEqualTo(252);
        assertThat(userInfoResponseResult.getCurrentRandomStreak()).isEqualTo(0);
        assertThat(userInfoResponseResult.getTeam()).isEqualTo(0);
        assertThat(userInfoResponseResult.getPoint()).isEqualTo(0);
        assertThat(userInfoResponseResult.getIsTodaySolved()).isFalse();
        assertThat(userInfoResponseResult.getIsYesterdaySolved()).isFalse();
        assertThat(userInfoResponseResult.getIsTodayRandomSolved()).isFalse();
        assertThat(userInfoResponseResult.getTodaySolvedProblemCount()).isEqualTo(0);
        assertThat(userInfoResponseResult.getMaxRandomStreak()).isEqualTo(0);
    }

    @Test
    public void save로_유저를_생성할_때_중복된_bojHandle의_유저를_생성하면_에러를_던진다() throws JsonProcessingException {
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

        // when
        testContainer.userService.save(userSave);

        // then
        assertThatThrownBy(() -> {
            testContainer.userService.save(userSave);
        }).isInstanceOf(EntityExistsException.class);
    }

    @Test
    public void save로_유저를_생성할_때_매니저가_0_또는_1_이_아니면_예외를_던진다() throws JsonProcessingException {
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
                .manager(2L)
                .emoji("🛠️")
                .build();

        // when & then
        assertThatThrownBy(() -> {
            testContainer.userService.save(userSave);
        }).isInstanceOf(IllegalArgumentException.class);
    }

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

        // when
        testContainer.userService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

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
        assertThatThrownBy(() -> {
            testContainer.userSolvedProblemService.findAllUserSolvedProblem("fin");
        }).isInstanceOf(IllegalArgumentException.class);
        // 유저 통계 삭제
        assertThat(testContainer.userStatisticsRepository.findByBojHandle("fin")).isNull();
        // 유저 JWT 토큰 삭제
        assertThat(testContainer.refreshTokenRepository.findByBojHandle("fin")).isNull();
        // 유저 나의 한마디 삭제
        assertThat(testContainer.boolshitRepository.findAll().size()).isEqualTo(0);
        // 유저 포인트 로그 삭제
        assertThat(testContainer.pointLogRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
        // 유저 경고 로그 삭제
        assertThat(testContainer.warningLogRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
        // 유저 아이템 삭제
        assertThat(testContainer.userItemRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
        // 유저 게시글 삭제
        assertThat(testContainer.boardRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
    }

    @Test
    public void delete를_이용해_존재하지_않는_유저를_삭제하면_예외를_던진다() {
        // given

        // when

        // then

    }

}
