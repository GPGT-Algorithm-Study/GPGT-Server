package com.randps.randomdefence.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.mock.FakeClock;
import com.randps.randomdefence.global.component.mock.FakeParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacDelayedParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceTest {

    private TestContainer testContainer;

    @Test
    @DisplayName("save를 이용하여 유저를 생성할 수 있다")
    public void saveUserTest() throws JsonProcessingException {
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
        testContainer = TestContainer.builder()
                .parser(new FakeParserImpl())
                .solvedacParser(new FakeSolvedacParserImpl(userScrapingInfoDto))
                .passwordEncoder(passwordEncoder)
            .clock(new FakeClock())
                .build();
        UserSave userSave = UserSave.builder()
            .bojHandle("fin1")
                .password("q1w2e3r4!")
                .notionId("성민")
                .manager(1L)
                .emoji("🛠️")
                .build();

        // when
        User userResult = testContainer.userService.save(userSave);
        UserRandomStreak userRandomStreakResult = testContainer.userRandomStreakService.findUserRandomStreak(
            "fin1");
        UserInfoResponse userInfoResponseResult = testContainer.userInfoService.getInfo("fin1");

        // then
        assertThat(userResult.getId()).isEqualTo(1L);
        assertThat(userResult.getBojHandle()).isEqualTo("fin1");
        assertThat(passwordEncoder.matches("q1w2e3r4!", userResult.getPassword())).isTrue();
        assertThat(userResult.getNotionId()).isEqualTo("성민");
        assertThat(userResult.getManager()).isTrue();
        assertThat(userResult.getEmoji()).isEqualTo("🛠️");

        assertThat(userRandomStreakResult.getId()).isEqualTo(1);
        assertThat(userRandomStreakResult.getBojHandle()).isEqualTo("fin1");
        assertThat(userRandomStreakResult.getMaxRandomStreak()).isEqualTo(0);
        assertThat(userRandomStreakResult.getCurrentRandomStreak()).isEqualTo(0);
        assertThat(userRandomStreakResult.getTodayRandomProblemId()).isEqualTo(0);
        assertThat(userRandomStreakResult.getIsTodayRandomSolved()).isFalse();
        assertThat(userRandomStreakResult.getIsKo()).isTrue();
        assertThat(userRandomStreakResult.getStartLevel()).isEqualTo("");
        assertThat(userRandomStreakResult.getEndLevel()).isEqualTo("");

        assertThat(userInfoResponseResult.getBojHandle()).isEqualTo("fin1");
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

        testContainer.userDeleteService.delete("fin1");
    }

    @Test
    @DisplayName("save로 유저를 생성하는데 중복된 bojHandle의 유저를 생성하면 에러를 던진다")
    public void saveDuplicatedUserExceptionTest() throws JsonProcessingException {
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
        testContainer = TestContainer.builder()
                .parser(new FakeParserImpl())
                .solvedacParser(new FakeSolvedacParserImpl(userScrapingInfoDto))
                .passwordEncoder(passwordEncoder)
            .clock(new FakeClock())
                .build();
        UserSave userSave = UserSave.builder()
            .bojHandle("testUser")
                .password("q1w2e3r4!")
                .notionId("성민")
                .manager(1L)
                .emoji("🛠️")
                .build();

        // when
        testContainer.userService.save(userSave);

        // then
        assertThatThrownBy(() -> testContainer.userService.save(userSave)).isInstanceOf(
            EntityExistsException.class);
    }

    @Test
    @DisplayName("save로 유저를 생성하는데 아직 유저 정보에 대한 Tracsaction이 반영되지 않았을 때, 중복된 bojHandle의 유저를 생성하면 에러를 던진다")
    public void saveConcurrencyExceptionTest() throws JsonProcessingException {
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
        testContainer = TestContainer.builder()
                .parser(new FakeParserImpl())
                .solvedacParser(new FakeSolvedacDelayedParserImpl(userScrapingInfoDto))
                .passwordEncoder(passwordEncoder)
            .clock(new FakeClock())
                .build();
        UserSave userSave = UserSave.builder()
            .bojHandle("fin2")
                .password("q1w2e3r4!")
                .notionId("성민")
                .manager(1L)
                .emoji("🛠️")
                .build();
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // when & then
        assertThatThrownBy(() -> {
            Future<?> future1 = pool.submit(()->{
                try {
                    testContainer.userService.save(userSave);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            Future<?> future2 = pool.submit(()->{
                try {
                    testContainer.userService.save(userSave);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                future1.get();
                future2.get();
            } catch (ExecutionException ee) {
                pool.shutdownNow();
                throw ee.getCause();
            }
            pool.shutdown();
        }).isInstanceOf(EntityExistsException.class);
    }

    @Test
    @DisplayName("save로 유저를 생성할 때 매니저가 0 또는 1이 아니면 예외를 던진다")
    public void saveUserManagerExceptionTest() throws JsonProcessingException {
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
        testContainer = TestContainer.builder()
                .parser(new FakeParserImpl())
                .solvedacParser(new FakeSolvedacParserImpl(userScrapingInfoDto))
                .passwordEncoder(passwordEncoder)
            .clock(new FakeClock())
                .build();
        UserSave userSave = UserSave.builder()
            .bojHandle("fin3")
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
