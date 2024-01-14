package com.randps.randomdefence.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import com.randps.randomdefence.domain.comment.dto.CommentPublishRequest;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.domain.user.dto.authDto.LoginRequest;
import com.randps.randomdefence.global.component.mock.FakeParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDeleteServiceTest {

    private TestContainer testContainer;

    @BeforeEach
    public void init() throws JsonProcessingException {
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
                .build();
        UserSave userSave = UserSave.builder()
                .bojHandle("fin")
                .password("q1w2e3r4!")
                .notionId("성민")
                .manager(1L)
                .emoji("🛠️")
                .build();
        testContainer.userService.save(userSave);
    }

    @Test
    public void delete를_이용해_유저를_bojHandle로_삭제할_수_있다() throws JsonProcessingException {
        // given

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 프로필 정보 삭제
        assertThatThrownBy(() -> {
            testContainer.userInfoService.getInfo("fin");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저의_랜덤_스트릭과_잔디도_삭제된다() throws JsonProcessingException {
        // given

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 랜덤 스트릭 삭제 & 유저 랜덤 스트릭 잔디 삭제
        assertThatThrownBy(() -> {
            testContainer.userRandomStreakService.findUserRandomStreak("fin");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저의_오늘푼_문제도_삭제된다() throws JsonProcessingException {
        // given

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();
        List<SolvedProblemDto> userSolvedProblemResults = testContainer.userSolvedProblemService.findAllUserSolvedProblem("fin");

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 오늘 푼 문제 삭제
        assertThat(userSolvedProblemResults.size()).isEqualTo(0);
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저의_통계도_삭제된다() throws JsonProcessingException {
        // given

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 통계 삭제
        assertThat(testContainer.userStatisticsRepository.findByBojHandle("fin")).isEmpty();
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저의_JWT_토큰도_삭제된다() throws JsonProcessingException {
        // given
        testContainer.userAuthService.login(LoginRequest.builder()
                .bojHandle("fin")
                .password("q1w2e3r4!")
                .build());

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 JWT 토큰 삭제
        assertThat(testContainer.refreshTokenRepository.findByBojHandle("fin")).isEmpty();
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저의_나의_한마디도_삭제된다() throws JsonProcessingException {
        // given
        testContainer.boolshitRepository.save(Boolshit.builder()
                .message("test")
                .bojHandle("fin")
                .build());

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 나의 한마디 삭제
        assertThat(testContainer.boolshitRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저의_포인트_로그도_삭제된다() throws JsonProcessingException {
        // given
        testContainer.pointLogSaveService.savePointLog("fin", 100, "test", true);

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 포인트 로그 삭제
        assertThat(testContainer.pointLogRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저의_경고_로그도_삭제된다() throws JsonProcessingException {
        // given
        testContainer.warningLogSaveService.saveWarningLog("fin", 1, "test", true);


        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 경고 로그 삭제
        assertThat(testContainer.warningLogRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저가_보유한_아이템도_삭제된다() throws JsonProcessingException {
        // given
        testContainer.itemSaveService.makeItem();
        testContainer.itemSaveService.buyItem("fin", 1L);

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 아이템 삭제
        assertThat(testContainer.userItemRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저가_작성한_게시글도_삭제된다() throws JsonProcessingException {
        // given
        testContainer.boardService.save("test", "fin", "test", "test", 1001, UUID.randomUUID().toString());

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 게시글 삭제
        assertThat(testContainer.boardRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
        assertThat(testContainer.boardImageRepository.findAllByBoardId(1L).isEmpty()).isTrue();
        assertThat(testContainer.imageRepository.findAll().isEmpty()).isTrue();
    }

    @Test
    public void delete를_이용해_유저를_삭제하면_유저가_작성한_모든_댓글도_삭제된다() throws JsonProcessingException {
        // given
        testContainer.commentService.save(CommentPublishRequest.builder()
                .boardId(1L)
                .bojHandle("fin")
                .content("test comment content")
                .build());

        // when
        testContainer.userDeleteService.delete("fin");
        List<User> userResults = testContainer.userRepository.findAll();

        // then
        assertThat(userResults.size()).isEqualTo(0);
        // 유저 댓글 삭제
        assertThat(testContainer.commentRepository.findAllByBojHandle("fin").isEmpty()).isTrue();
    }

    @Test
    public void delete를_이용해_존재하지_않는_유저를_삭제하면_예외를_던진다() {
        // given

        // when & then
        assertThatThrownBy(() -> {
            testContainer.userDeleteService.delete("does not exist user");
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
