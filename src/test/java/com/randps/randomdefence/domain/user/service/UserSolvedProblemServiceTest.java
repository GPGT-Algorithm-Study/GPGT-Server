package com.randps.randomdefence.domain.user.service;

import static com.randps.randomdefence.global.component.util.TimeUtil.getToday;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.mock.FakeBojDelayedParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserSolvedProblemServiceTest {

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
    testContainer.userService.save(userSave);
    ScrapingUserLog log = testContainer.scrapingUserLogRepository.findByBojHandle("fin").get();
    log.saveLastScrapingTime(LocalDateTime.now().minusMinutes(40));
    testContainer.scrapingUserLogRepository.save(log);
  }

  @AfterEach
  void tearDown() {
    testContainer.userDeleteService.delete("fin");
  }

  @Test
  @DisplayName("유저가 06:00 ~ 06:00(24시간) 사에에 푼 문제라면 True를 반환한다.")
  public void isTodaySolvedTest() throws JsonProcessingException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime((getToday().plusHours(12).plusSeconds(1)).toString()) // 오늘 시작한 뒤 1초 뒤에 푼 문제
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    // when
    testContainer.scrapingUserController.scrapingUserData("fin");
    boolean result = testContainer.userSolvedProblemService.isTodaySolved("fin");

    // then
    assertThat(result).isEqualTo(true);
  }

  @Test
  @DisplayName("유저가 6:00:01(하루 시작의 1초 뒤) 시점에 푼 문제더라도 오늘 푼 문제로 크롤링된다.")
  public void isTodaySolvedAtDayStartTest() throws JsonProcessingException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime((getToday().plusSeconds(1)).toString()) // 오늘 시작한 뒤 1초 뒤에 푼 문제
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    // when
    testContainer.scrapingUserController.scrapingUserData("fin");
    boolean result = testContainer.userSolvedProblemService.isTodaySolved("fin");

    // then
    assertThat(result).isEqualTo(true);
  }


  @Test
  @DisplayName("유저가 5:59:59(하루 끝의 1초 전) 시점에 푼 문제더라도 오늘 푼 문제로 크롤링된다.")
  public void isTodaySolvedAtDayEndTest() throws JsonProcessingException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime((getToday().plusDays(1).minusSeconds(1)).toString()) // 오늘이 끝나기 1초 전에 푼 문제
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    // when
    testContainer.scrapingUserController.scrapingUserData("fin");
    boolean result = testContainer.userSolvedProblemService.isTodaySolved("fin");

    // then
    assertThat(result).isEqualTo(true);
  }

  @Test
  @DisplayName("유저가 어제 5:59:59 시점에 푼 문제는 오늘 푼 문제로 크롤링되지 않는다.")
  public void isTodaySolvedAtYesterdayEndTest() throws JsonProcessingException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime((getToday().minusSeconds(1)).toString()) // 오늘이 끝나기 1초 전에 푼 문제
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    // when
    testContainer.scrapingUserController.scrapingUserData("fin");
    boolean result = testContainer.userSolvedProblemService.isTodaySolved("fin");

    // then
    assertThat(result).isEqualTo(false);
  }

  @Test
  @DisplayName("유저가 특정 날짜에 푼 문제들을 지정해서 가져올 수 있다.")
  public void findAllSomedayUserSolvedProblemTest() throws JsonProcessingException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime(
                (LocalDateTime.of(2024, 3, 19, 10, 10, 10)).toString()) // 2024-3-19 10:10:10에 푼 문제
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    // when
    testContainer.scrapingUserController.scrapingUserData("fin"); // 유저가 해결한 문제 크롤링
    List<SolvedProblemDto> ret = testContainer.userSolvedProblemService.findAllSomedayUserSolvedProblem(
        "fin", LocalDateTime.of(2024, 3, 19, 16, 25, 0));

    // then
    assertThat(ret.size()).isEqualTo(1);
    assertThat(ret.get(0).getProblemId()).isEqualTo(1000);
    assertThat(ret.get(0).getTitle()).isEqualTo("A+B");
    assertThat(ret.get(0).getDateTime()).isEqualTo(
        LocalDateTime.of(2024, 3, 19, 10, 10, 10).toString());
    assertThat(ret.get(0).getLanguage()).isEqualTo("C++");
  }

}
