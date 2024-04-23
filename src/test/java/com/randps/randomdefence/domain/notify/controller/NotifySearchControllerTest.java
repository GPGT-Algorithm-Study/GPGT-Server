package com.randps.randomdefence.domain.notify.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.dto.NotifyResponse;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.mock.FakeBojParserImpl;
import com.randps.randomdefence.global.component.mock.FakeClock;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class NotifySearchControllerTest {

  private TestContainer testContainer;

  @BeforeEach
  void setUp() {
    /* 크롤링 데이터 세팅 */
    UserScrapingInfoDto userScrapingInfoDto = UserScrapingInfoDto.builder().tier(15)
        .profileImg("https://static.solved.ac/uploads/profile/64x64/fin-picture-1665752455693.png")
        .currentStreak(252).totalSolved(1067).isTodaySolved(true).todaySolvedProblemCount(1)
        .build();
    /* 테스트 컨테이너 초기화 */
    testContainer = TestContainer.builder()
        .parser(new FakeBojParserImpl(new ArrayList<>()))
        .passwordEncoder(new BCryptPasswordEncoder())
        .solvedacParser(new FakeSolvedacParserImpl(userScrapingInfoDto))
        .clock(new FakeClock())
        .build();
    /* 테스트 유저 생성 */
    UserSave adminUser = UserSave.builder().bojHandle("admin").password("q1w2e3r4!").notionId("관리자")
        .manager(1L).emoji("🛠️").build(); // 관리자
    UserSave normalUser = UserSave.builder().bojHandle("normal").password("q1w2e3r4!2")
        .notionId("일반유저").manager(0L).emoji("🛠️").build(); // 일반 유저
    try {
      testContainer.userService.save(adminUser);
      testContainer.userService.save(normalUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("사용자는 자신에게 온 모든 알림을 조회할 수 있다.")
  void searchByReceiver() {
    // given
    String normalUserToken = testContainer.jwtUtil.createToken("normal", "refresh", "ROLE_USER");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림1")
        .type(NotifyType.EVENT)
        .build());
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림2")
        .type(NotifyType.MESSAGE)
        .build());

    // when
    List<NotifyResponse> responses = null;
    try {
      responses = testContainer.notifySearchController.searchByReceiver(normalUserToken, "normal");
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    // then
    assertThat(responses.size()).isEqualTo(2);
    assertThat(responses.get(0).getMessage()).isEqualTo("테스트 알림1");
    assertThat(responses.get(1).getMessage()).isEqualTo("테스트 알림2");

  }

}
