package com.randps.randomdefence.domain.scraping.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.problem.domain.Problem;
import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.mock.FakeBojDelayedParserImpl;
import com.randps.randomdefence.global.component.mock.FakeClock;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
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
    testContainer = TestContainer.builder()
        .parser(fakeBojDelayedParserImpl)
        .solvedacParser(new FakeSolvedacParserImpl(userScrapingInfoDto))
        .passwordEncoder(passwordEncoder)
        .clock(new FakeClock())
        .build();
    UserSave userSave = UserSave.builder().bojHandle("fin").password("q1w2e3r4!").notionId("성민")
        .manager(1L).emoji("🛠️").build();
    UserSave userSave2 = UserSave.builder().bojHandle("testUser").password("q1w2e3r4!2")
        .notionId("성민2").manager(1L).emoji("🛠️").build();
    testContainer.userService.save(userSave);
    testContainer.userService.save(userSave2);
    ScrapingUserLog log1 = testContainer.scrapingUserLogRepository.findByBojHandle("fin").get();
    log1.saveLastScrapingTime(LocalDateTime.now().minusMinutes(40));
    testContainer.scrapingUserLogRepository.save(log1);
    ScrapingUserLog log2 = testContainer.scrapingUserLogRepository.findByBojHandle("testUser")
        .get();
    log2.saveLastScrapingTime(LocalDateTime.now().minusMinutes(40));
    testContainer.scrapingUserLogRepository.save(log2);
  }

  @AfterEach
  void tearDown() {
    testContainer.userDeleteService.delete("fin");
    testContainer.userDeleteService.delete("testUser");
    testContainer = null;
    fakeBojDelayedParserImpl = null;
  }

  @Test
  @DisplayName("서버 자동 스크래핑중에 유저 정보 스크래핑이 동시에 실행 되면 요청에 실패한다.")
  void userInfoScrapingConcurrencyTest()
      throws InterruptedException, JsonProcessingException, ExecutionException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime((testContainer.timeUtil.getToday().plusHours(2).plusSeconds(1)).toString())
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    /* DB에 존재하지 않는 문제는 크롤링해서 DB에 저장 후, 리턴하기 때문에 DB에 문제 정보를 넣어놔야 크롤링이 동작하지 않는다. */
    testContainer.problemRepository.save(
        Problem.builder().id(null).acceptedUserCount(271093).averageTries("2.5279")
            .givesNoRating(false).isLevelLocked(true).isPartial(false).isSolvable(true).level(1)
            .official(true).problemId(1000).sprout(true).titleKo("A+B").votedUserCount(170)
            .build()); // 문제 정보를 저장

    // when & then
    ExecutorService pool = Executors.newFixedThreadPool(2);

    pool.execute(() -> {
      try {
        testContainer.scheduler.everyTermJob();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    Future<?> future = pool.submit(() -> {
      try {
        ResponseEntity<Map<String, String>> ret = testContainer.scrapingUserController.scrapingUserData(
            "fin");
        return ret.getBody().get("message");
      } catch (JsonProcessingException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException(e);
      }
    });

    if (pool.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) {
      pool.shutdown();
    } else {
      pool.shutdownNow();
    }

    assertThat(future.get()).isEqualTo("서버가 현재 데이터 크롤링 중입니다.");
    assertThat(testContainer.userSolvedProblemService.findAllUserSolvedProblem("fin")
        .size()).isEqualTo(1);
  }


  @Test
  @DisplayName("서버 자동 스크래핑 중이 아닐 때, 유저 정보 스크래핑이 실행되면 요청에 성공한다.")
  void userInfoScrapingValidTest() {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime((testContainer.timeUtil.getToday().plusHours(2).plusSeconds(1)).toString())
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    /* DB에 존재하지 않는 문제는 크롤링해서 DB에 저장 후, 리턴하기 때문에 DB에 문제 정보를 넣어놔야 크롤링이 동작하지 않는다. */
    testContainer.problemRepository.save(
        Problem.builder().id(null).acceptedUserCount(271093).averageTries("2.5279")
            .givesNoRating(false).isLevelLocked(true).isPartial(false).isSolvable(true).level(1)
            .official(true).problemId(1000).sprout(true).titleKo("A+B").votedUserCount(170)
            .build()); // 문제 정보를 저장

    // when & then
    ResponseEntity<Map<String, String>> ret;
    try {
      ret = testContainer.scrapingUserController.scrapingUserData(
          "fin");
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException(e);
    }

    assertThat(ret.getBody().get("code")).isEqualTo("200");
    assertThat(ret.getBody().get("type")).isEqualTo("OK");
    assertThat(ret.getBody().get("message")).isEqualTo("요청을 성공했습니다.");
    assertThat(testContainer.userSolvedProblemService.findAllUserSolvedProblem("fin")
        .size()).isEqualTo(1);
  }

}
