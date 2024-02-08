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
import com.randps.randomdefence.global.component.mock.FakeSolvedacDelayedParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
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

    static class UserSaveTestThread implements Runnable {

        private final TestContainer testContainer;

        private final UserSave userSave;

        UserSaveTestThread(TestContainer testContainer, UserSave userSave) {
            this.testContainer = testContainer;
            this.userSave = userSave;
        }

        @Override
        public void run() throws EntityExistsException {
            try {
                testContainer.userService.save(userSave);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    @DisplayName("save로 유저를 생성하는데 아직 유저 정보에 대한 Tracsaction이 반영되지 않았을 때, 중복된 bojHandle의 유저를 생성하면 에러를 던진다")
    public void save로_유저를_생성할_때_빠르게_연속으로_중복된_bojHandle의_유저를_생성하면_에러를_던진다() throws JsonProcessingException {
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
                .solvedacParser(new FakeSolvedacDelayedParserImpl(userScrapingInfoDto))
                .passwordEncoder(passwordEncoder)
                .build();
        UserSave userSave = UserSave.builder()
                .bojHandle("fin")
                .password("q1w2e3r4!")
                .notionId("성민")
                .manager(1L)
                .emoji("🛠️")
                .build();
        Thread userSaveProcess1 = new Thread(new UserSaveTestThread(testContainer, userSave));
        Thread userSaveProcess2 = new Thread(new UserSaveTestThread(testContainer, userSave));

        // when & then
        assertThatThrownBy(() -> {
            userSaveProcess1.start();
            userSaveProcess2.start();
            userSaveProcess1.join();
            userSaveProcess2.join();
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

}
