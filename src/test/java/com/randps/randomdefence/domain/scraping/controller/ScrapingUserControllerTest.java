package com.randps.randomdefence.domain.scraping.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.problem.domain.Problem;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.mock.FakeBojDelayedParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ScrapingUserControllerTest {

  private FakeBojDelayedParserImpl fakeBojDelayedParserImpl;

  private TestContainer testContainer;

  @BeforeEach
  void setUp() throws JsonProcessingException {
    UserScrapingInfoDto userScrapingInfoDto = UserScrapingInfoDto.builder().tier(15)
        .profileImg("https://static.solved.ac/uploads/profile/64x64/fin-picture-1665752455693.png")
        .currentStreak(252).totalSolved(1067).isTodaySolved(true).todaySolvedProblemCount(1)
        .build();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    fakeBojDelayedParserImpl = new FakeBojDelayedParserImpl(List.of());
    fakeBojDelayedParserImpl.delayOff();
    testContainer = TestContainer.builder().parser(fakeBojDelayedParserImpl)
        .solvedacParser(new FakeSolvedacParserImpl(userScrapingInfoDto))
        .passwordEncoder(passwordEncoder).build();
    UserSave userSave = UserSave.builder().bojHandle("fin").password("q1w2e3r4!").notionId("성민")
        .manager(1L).emoji("🛠️").build();
    UserSave userSave2 = UserSave.builder().bojHandle("testUser").password("q1w2e3r4!2")
        .notionId("성민2").manager(1L).emoji("🛠️").build();
    testContainer.userService.save(userSave);
    testContainer.userService.save(userSave2);
  }

  @Test
  @DisplayName("유저 정보 스크래핑은 서버 자동 스크래핑과 동시에 실행 되더라도 같은 문제를 2번 스크래핑해서는 안된다.")
  void userInfoScrapingConcurrencyTest() throws InterruptedException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B").dateTime("2022-01-01 00:00:00")
            .language("C++").build());
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems);
    fakeBojDelayedParserImpl.delayOn();

    /* DB에 존재하지 않는 문제는 크롤링해서 DB에 저장 후, 리턴하기 때문에 DB에 문제 정보를 넣어놔야 크롤링이 동작하지 않는다. */
    testContainer.problemRepository.save(
        Problem.builder().id(null).acceptedUserCount(271093).averageTries("2.5279")
            .givesNoRating(false).isLevelLocked(true).isPartial(false).isSolvable(true).level(1)
            .official(true).problemId(1000).sprout(true).titleKo("A+B").votedUserCount(170)
            .build());

    // when & then
    ExecutorService pool = Executors.newFixedThreadPool(2);

    assertThatThrownBy(() -> {
      Future<?> retServer = pool.submit(() -> {
        try {
          testContainer.scheduler.everyTermJob();
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      });
      Future<?> retUserSubmit = pool.submit(() -> {
        try {
          testContainer.scrapingUserController.scrapingUserData("fin");
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      });
      try {
        retServer.get();
        retUserSubmit.get();
      } catch (ExecutionException ee) {
        pool.shutdownNow();
        throw ee.getCause();
      }
      pool.shutdown();
    }).isInstanceOf(RuntimeException.class);

  }

}
