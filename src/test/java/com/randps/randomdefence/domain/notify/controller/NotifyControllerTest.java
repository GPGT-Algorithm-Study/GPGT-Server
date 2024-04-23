package com.randps.randomdefence.domain.notify.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.dto.NotifyDeleteRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyPublishRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyPublishToUsersRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyReadRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyUpdateRequest;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.mock.FakeBojParserImpl;
import com.randps.randomdefence.global.component.mock.FakeClock;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class NotifyControllerTest {

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
  @DisplayName("관리자는 특정인에게 알림을 발행할 수 있다.")
  void publish() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh",
        "ROLE_USER, ROLE_ADMIN");
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

    NotifyPublishRequest request = NotifyPublishRequest.builder()
        .receiver("normal")
        .message("테스트 알림3")
        .type(NotifyType.MESSAGE)
        .build();

    // when
    ResponseEntity<Map<String, String>> response = null;
    try {
      response = testContainer.notifyController.publish(request, adminUserToken);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    // then
    assertThat(response.getBody().get("message")).isEqualTo("알림을 성공적으로 발행했습니다.");
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(testContainer.notifyRepository.findByReceiver("normal").size()).isEqualTo(2);
  }

  @Test
  @DisplayName("관리자는 특정 알림의 내용을 수정할 수 있다.")
  void update() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh",
        "ROLE_USER, ROLE_ADMIN");
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

    NotifyUpdateRequest request = NotifyUpdateRequest.builder()
        .id(1L)
        .message("테스트 알림 수정")
        .type(NotifyType.MESSAGE)
        .build();

    // when
    ResponseEntity<Map<String, String>> response = null;
    try {
      response = testContainer.notifyController.update(request, adminUserToken);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }
    List<Notify> notifies = testContainer.notifyRepository.findByReceiver("normal");

    // then
    assertThat(response.getBody().get("message")).isEqualTo("알림을 성공적으로 수정했습니다.");
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(notifies.size()).isEqualTo(1);
    assertThat(notifies.get(0).getMessage()).isEqualTo("테스트 알림 수정");
  }

  @Test
  @DisplayName("관리자는 특정 알림을 삭제할 수 있다.")
  void delete() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh",
        "ROLE_USER, ROLE_ADMIN");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림1")
        .type(NotifyType.EVENT)
        .build());

    NotifyDeleteRequest request = NotifyDeleteRequest.builder()
        .id(1L)
        .build();

    // when
    ResponseEntity<Map<String, String>> response = null;
    try {
      response = testContainer.notifyController.delete(request, adminUserToken);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }
    List<Notify> notifies = testContainer.notifyRepository.findByReceiver("normal");

    // then
    assertThat(response.getBody().get("message")).isEqualTo("알림을 성공적으로 삭제했습니다.");
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(notifies.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("관리자는 특정 유저들에게 한 번에 동일한 알림을 발행할 수 있다.")
  void publishToUsers() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh",
        "ROLE_USER, ROLE_ADMIN");
    NotifyPublishToUsersRequest request = NotifyPublishToUsersRequest.builder()
        .receivers(List.of("normal", "admin"))
        .message("테스트 알림")
        .type(NotifyType.MESSAGE)
        .build();

    // when
    ResponseEntity<Map<String, String>> response = null;
    try {
      response = testContainer.notifyController.publishToUsers(request, adminUserToken);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    // then
    assertThat(response.getBody().get("message")).isEqualTo("알림을 성공적으로 발행했습니다.");
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(testContainer.notifyRepository.findAll().size()).isEqualTo(2);
  }

  @Test
  @DisplayName("알림 수신인은 알림을 읽음 처리할 수 있다.")
  void read() {
    // given
    String normalUserToken = testContainer.jwtUtil.createToken("normal", "refresh", "ROLE_USER");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림1")
        .type(NotifyType.EVENT)
        .build());

    NotifyReadRequest request = NotifyReadRequest.builder()
        .id(1L)
        .build();

    // when
    ResponseEntity<Map<String, String>> response = null;
    try {
      response = testContainer.notifyController.read(request, normalUserToken);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }
    List<Notify> notifies = testContainer.notifyRepository.findByReceiver("normal");

    // then
    assertThat(response.getBody().get("message")).isEqualTo("알림을 성공적으로 읽었습니다.");
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(notifies.size()).isEqualTo(1);
  }

  // TODO: replace to WebTestClient test
  /*
  @Test
  @DisplayName("알림 수신인이 아닌 사람은 관리자라 하더라도 알림을 읽음 처리할 수 없다.")
  void invalidRead() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh",
        "ROLE_USER, ROLE_ADMIN");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림1")
        .type(NotifyType.EVENT)
        .build());

    NotifyReadRequest request = NotifyReadRequest.builder()
        .id(1L)
        .build();

    // when & then
    assertThatThrownBy(() -> testContainer.notifyController.read(request, adminUserToken))
        .isInstanceOf(AccessDeniedException.class);
  }
   */
}
