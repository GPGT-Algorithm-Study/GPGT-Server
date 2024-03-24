package com.randps.randomdefence.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.mock.FakeBojDelayedParserImpl;
import com.randps.randomdefence.global.component.mock.FakeClock;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserSolvedProblemMockingTest {

  private TestContainer testContainer;

  private FakeBojDelayedParserImpl fakeBojDelayedParserImpl;

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
        .passwordEncoder(passwordEncoder)
        .clock(new FakeClock()).build();
    UserSave userSave = UserSave.builder().bojHandle("testUserT").password("q1w2e3r4!").notionId("성민")
        .manager(1L).emoji("🛠️").build();
    testContainer.userService.save(userSave);
    ScrapingUserLog log = testContainer.scrapingUserLogRepository.findByBojHandle("testUserT").get();
    log.saveLastScrapingTime(testContainer.timeUtil.getToday().minusMinutes(20));
    testContainer.scrapingUserLogRepository.save(log);
  }

  @AfterEach
  void tearDown() {
    testContainer.userDeleteService.delete("testUserT");
    testContainer = null;
  }

  @Test
  @DisplayName("오늘 처음으로 진행되는 크롤링은 전날의 마지막 문제들을 크롤링한다.")
  public void dayFirstCrawlingTest() throws JsonProcessingException {
    // given
    List<Object> solvedProblems = List.of(
        BojProblemPair.builder().problemId(1000).title("A+B")
            .dateTime((testContainer.timeUtil.getToday().minusMinutes(15)
                .minusSeconds(1)).toString()) // 오늘 시작한 뒤 1초 뒤에 푼 문제
            .language("C++").build()); // 유저가 해결한 문제
    fakeBojDelayedParserImpl.setSolvedProblems(solvedProblems); // 유저가 해결한 문제를 설정
    fakeBojDelayedParserImpl.delayOn(); // 크롤링 지연을 설정

    // when
    testContainer.scheduler.everyTermJob();

    // then
    assertThat(testContainer.userSolvedProblemService.findAllUserSolvedProblem("testUserT").size()).isEqualTo(1);
    assertThat(testContainer.userInfoService.getInfo("testUserT").getIsTodaySolved()).isEqualTo(false);
  }

}
