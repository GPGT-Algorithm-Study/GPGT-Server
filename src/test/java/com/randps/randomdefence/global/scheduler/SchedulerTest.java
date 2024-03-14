package com.randps.randomdefence.global.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.mock.FakeParserImpl;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SchedulerTest {

  private TestContainer testContainer;

  @BeforeEach
  void setUp() throws JsonProcessingException {
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

  @AfterEach
  void tearDown() {
    testContainer.userDeleteService.delete("fin");
    testContainer = null;
  }

  @Test
  @DisplayName("정해진 시간마다 실행되는 스크래핑 테스트 (매 20분 간격)")
  void everyTermJobTest() {

  }

  @Test
  @DisplayName("정해진 시간마다 실행되는 스크래핑 테스트 (하루 간격, 매일 새벽 6시 25분)")
  void everyDayTermJobTest() {

  }

  @Test
  @DisplayName("정해진 시간마다 실행되는 스크래핑 테스트 (하루 간격, 매일 새벽 6시 25분)")
  void weekInitJobTest() {

  }

}
