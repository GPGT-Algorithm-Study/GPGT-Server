package com.randps.randomdefence.domain.notify.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.dto.NotifyReadRequest;
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

public class NotifyAdminSearchControllerTest {

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
  @DisplayName("관리자는 존재하는 모든 알림을 조회할 수 있다.")
  void searchAll() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림1")
        .type(NotifyType.EVENT)
        .build());
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("admin")
        .message("테스트 알림2")
        .type(NotifyType.EVENT)
        .build());

    // when
    List<NotifyResponse> response = null;
    try {
      response = testContainer.notifyAdminSearchController.searchAll(adminUserToken);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    // then
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(2);
    assertThat(response.get(0).getMessage()).isEqualTo("테스트 알림1");
    assertThat(response.get(1).getMessage()).isEqualTo("테스트 알림2");

  }

  @Test
  @DisplayName("관리자는 특정 사용자에게 온 모든 알림을 조회할 수 있다.")
  void searchByReceiver() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림1")
        .type(NotifyType.EVENT)
        .build());
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("admin")
        .message("테스트 알림2")
        .type(NotifyType.EVENT)
        .build());

    // when
    List<NotifyResponse> response1 = null;
    List<NotifyResponse> response2 = null;
    try {
      response1 = testContainer.notifyAdminSearchController.searchByReceiver(adminUserToken,
          "normal");
      response2 = testContainer.notifyAdminSearchController.searchByReceiver(adminUserToken,
          "admin");
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    // then
    assertThat(response1).isNotNull();
    assertThat(response1.size()).isEqualTo(1);
    assertThat(response1.get(0).getMessage()).isEqualTo("테스트 알림1");
    assertThat(response2).isNotNull();
    assertThat(response2.size()).isEqualTo(1);
    assertThat(response2.get(0).getMessage()).isEqualTo("테스트 알림2");

  }

  @Test
  @DisplayName("관리자는 아직 사람들이 읽지 않은 모든 알림을 조회할 수 있다.")
  void searchNotReadNotifiesAll() {
    // given
    String normalUserToken = testContainer.jwtUtil.createToken("normal", "refresh");
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림1")
        .type(NotifyType.EVENT)
        .build());
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림2")
        .type(NotifyType.EVENT)
        .build());
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("admin")
        .message("테스트 알림3")
        .type(NotifyType.EVENT)
        .build());
    NotifyReadRequest request = NotifyReadRequest.builder().id(1L).build();

    // when
    List<NotifyResponse> response = null;
    try {
      testContainer.notifyController.read(request, normalUserToken);
      response = testContainer.notifyAdminSearchController.searchNotReadNotifiesAll(adminUserToken);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    for (NotifyResponse notifyResponse : response) {
      System.out.println(notifyResponse);
    }

    // then
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(2);
    assertThat(response.get(0).getMessage()).isEqualTo("테스트 알림2");
    assertThat(response.get(1).getMessage()).isEqualTo("테스트 알림3");

  }

}
