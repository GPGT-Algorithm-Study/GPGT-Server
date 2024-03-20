package com.randps.randomdefence.domain.notify.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.dto.NotifyDeleteRequest;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.user.dto.UserSave;
import com.randps.randomdefence.global.component.mock.FakeBojParserImpl;
import com.randps.randomdefence.global.component.mock.FakeClock;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import com.randps.randomdefence.global.component.parser.dto.UserScrapingInfoDto;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class NotifyAdminControllerTest {

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
  @DisplayName("관리자는 특정 유저의 모든 알림을 삭제할 수 있다.")
  void deleteAllByReceiver() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림")
        .type(NotifyType.EVENT)
        .build());

    // when
    ResponseEntity<Map<String, String>> response = null;
    try {
      response = testContainer.notifyAdminController.deleteAllByReceiver(adminUserToken, "normal");
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    // then
    assertThat(response.getBody().get("message")).isEqualTo("해당 유저의 모든 알림을 성공적으로 삭제했습니다.");
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(testContainer.notifyRepository.findByReceiver("normal").size()).isEqualTo(0);
  }

  @Test
  @DisplayName("관리자는 특정 알림을 삭제할 수 있다.")
  void deleteById() {
    // given
    String adminUserToken = testContainer.jwtUtil.createToken("admin", "refresh");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림")
        .type(NotifyType.EVENT)
        .build());
    NotifyDeleteRequest request = NotifyDeleteRequest.builder()
        .id(testContainer.notifyRepository.findByReceiver("normal").get(0).getId())
        .build();

    // when
    ResponseEntity<Map<String, String>> response = null;
    try {
      response = testContainer.notifyAdminController.deleteById(adminUserToken, request);
    } catch (Exception e) {
      e.printStackTrace();
      Assertions.fail();
    }

    // then
    assertThat(response.getBody().get("message")).isEqualTo("알림을 성공적으로 삭제했습니다.");
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(testContainer.notifyRepository.findByReceiver("normal").size()).isEqualTo(0);
  }

  @Test
  @DisplayName("일반 유저가 알림을 삭제하면 예외가 발생한다.")
  void invalidDelete() {
    // given
    String normalUserToken = testContainer.jwtUtil.createToken("normal", "refresh");
    testContainer.notifyRepository.save(Notify.builder()
        .receiver("normal")
        .message("테스트 알림")
        .type(NotifyType.EVENT)
        .build());
    NotifyDeleteRequest request = NotifyDeleteRequest.builder()
        .id(testContainer.notifyRepository.findByReceiver("normal").get(0).getId())
        .build();

    // when & then
    assertThatThrownBy(
        () -> testContainer.notifyAdminController.deleteById(normalUserToken, request))
        .isInstanceOf(AccessDeniedException.class);
    assertThatThrownBy(
        () -> testContainer.notifyAdminController.deleteAllByReceiver(normalUserToken, "normal"))
        .isInstanceOf(AccessDeniedException.class);
  }

}
