package com.randps.randomdefence.domain.complaint.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.dto.ComplaintDeleteRequest;
import com.randps.randomdefence.domain.complaint.dto.ComplaintDetailResponse;
import com.randps.randomdefence.domain.complaint.dto.ComplaintSaveRequest;
import com.randps.randomdefence.domain.complaint.dto.ComplaintUpdateRequest;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.mock.TestContainer;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.global.component.mock.FakeBojParserImpl;
import com.randps.randomdefence.global.component.mock.FakeClock;
import com.randps.randomdefence.global.component.mock.FakeSolvedacParserImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ComplaintRequesterControllerTest {

  private TestContainer testContainer;

  @BeforeEach
  void setUp() {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    testContainer = TestContainer.builder()
        .passwordEncoder(passwordEncoder)
        .parser(new FakeBojParserImpl(List.of()))
        .solvedacParser(new FakeSolvedacParserImpl(null))
        .clock(new FakeClock())
        .build();

    // 테스트 관리자 유저 생성
    testContainer.userRepository.save(User.builder()
        .bojHandle("fing9")
        .password(passwordEncoder.encode("q1w2e3r4!"))
        .roles("ROLE_USER, ROLE_ADMIN")
        .notionId("관리자")
        .manager(true)
        .warning(0)
        .profileImg(null)
        .emoji("🔥")
        .tier(15)
        .totalSolved(1100)
        .currentStreak(310)
        .currentRandomStreak(160)
        .team(0)
        .point(1000)
        .isTodayRandomSolved(false)
        .isYesterdaySolved(false)
        .isTodayRandomSolved(false)
        .todaySolvedProblemCount(0)
        .build());

    // 테스트 일반 유저 생성
    testContainer.userRepository.save(User.builder()
        .bojHandle("normalUser")
        .password(passwordEncoder.encode("q1w2e3r4!"))
        .roles("ROLE_USER")
        .notionId("일반 유저")
        .manager(false)
        .warning(0)
        .profileImg(null)
        .emoji("🔥")
        .tier(15)
        .totalSolved(1100)
        .currentStreak(310)
        .currentRandomStreak(160)
        .team(0)
        .point(1000)
        .isTodayRandomSolved(false)
        .isYesterdaySolved(false)
        .isTodayRandomSolved(false)
        .todaySolvedProblemCount(0)
        .build());

    // 테스트 일반 유저 생성
    testContainer.userRepository.save(User.builder()
        .bojHandle("normalUser2")
        .password(passwordEncoder.encode("q1w2e3r4!"))
        .roles("ROLE_USER")
        .notionId("두번째 일반 유저")
        .manager(false)
        .warning(0)
        .profileImg(null)
        .emoji("🔥")
        .tier(15)
        .totalSolved(1100)
        .currentStreak(310)
        .currentRandomStreak(160)
        .team(0)
        .point(1000)
        .isTodayRandomSolved(false)
        .isYesterdaySolved(false)
        .isTodayRandomSolved(false)
        .todaySolvedProblemCount(0)
        .build());
  }

  @AfterEach
  void tearDown() {
    Optional<User> adminUser = testContainer.userRepository.findByBojHandle("fing9");
    Optional<User> normalUser = testContainer.userRepository.findByBojHandle("normalUser");
    Optional<User> normalUser2 = testContainer.userRepository.findByBojHandle("normalUser2");
    adminUser.ifPresent(user -> testContainer.userRepository.delete(user));
    normalUser.ifPresent(user -> testContainer.userRepository.delete(user));
    normalUser2.ifPresent(user -> testContainer.userRepository.delete(user));
    testContainer = null;
  }

  @Test
  @DisplayName("일반 유저는 자기 자신의 모든 민원을 조회할 수 있다.")
  void searchAllTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser")
        .content("두번째 민원")
        .processor("fing9")
        .reply("두번째 민원 답변")
        .build();
    Complaint newComplaint3 = Complaint.builder()
        .requester("normalUser2")
        .content("세번째 민원")
        .processor("fing9")
        .reply("세번째 민원 답변")
        .build();
    Complaint newComplaint4 = Complaint.builder()
        .requester("normalUser2")
        .content("네번째 민원")
        .processor("fing9")
        .reply("네번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    testContainer.complaintRepository.save(newComplaint3);
    testContainer.complaintRepository.save(newComplaint4);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId(), normalUser.getRoles());
    List<ComplaintDetailResponse> allComplaints = new ArrayList<>();

    // when
    try {
      allComplaints = testContainer.complaintRequesterController
          .searchAll(refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // then
    assertThat(allComplaints.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("유저는 민원을 등록할 수 있다.")
  void registerTest() {
    // given
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId(), normalUser.getRoles());
    ComplaintSaveRequest complaintSaveRequest = ComplaintSaveRequest.builder()
        .requester("normalUser")
        .content("다섯번째 민원")
        .complaintType(ComplaintType.BUG)
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when
    try {
      response = testContainer.complaintRequesterController
          .register(complaintSaveRequest, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    List<Complaint> allComplaints = testContainer.complaintRepository.findAll();

    // then
    assertThat(allComplaints.size()).isEqualTo(1);
    assertThat(response.getBody().get("type")).isEqualTo(HttpStatus.OK.getReasonPhrase());
    assertThat(response.getBody().get("code")).isEqualTo("200");
    assertThat(response.getBody().get("message")).isEqualTo("민원을 성공적으로 등록했습니다.");
  }

  // TODO: replace to WebTestClient test
  /*
  @Test
  @DisplayName("유저가 다른 유저의 이름으로 민원을 등록하면 예외가 발생한다.")
  void invalidRegisterTest() {
    // given
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId(), normalUser.getRoles());
    ComplaintSaveRequest complaintSaveRequest = ComplaintSaveRequest.builder()
        .requester("normalUser2")
        .content("다섯번째 민원")
        .complaintType(ComplaintType.BUG)
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintRequesterController
          .register(complaintSaveRequest, refresh_token);
    }).isInstanceOf(AccessDeniedException.class);
 }
   */

  @Test
  @DisplayName("유저는 민원을 수정할 수 있다.")
  void updateTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId(), normalUser.getRoles());
    ComplaintUpdateRequest request = ComplaintUpdateRequest.builder()
        .id(1L)
        .content("첫번째 민원 수정")
        .complaintType(ComplaintType.NEW_FUNCTION)
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when
    try {
      response = testContainer.complaintRequesterController
          .update(request, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Optional<Complaint> complaint = testContainer.complaintRepository.findById(1L);

    // then
    assertThat(response.getBody().get("type")).isEqualTo(HttpStatus.OK.getReasonPhrase());
    assertThat(response.getBody().get("code")).isEqualTo("200");
    assertThat(response.getBody().get("message")).isEqualTo("민원을 성공적으로 수정했습니다.");
    assertThat(complaint.isPresent()).isEqualTo(true);
    assertThat(complaint.get().getContent()).isEqualTo(request.getContent());
    assertThat(complaint.get().getComplaintType()).isEqualTo(ComplaintType.NEW_FUNCTION);
  }

  // TODO: replace to WebTestClient test
  /*
  @Test
  @DisplayName("일반 유저는 타인의 민원을 수정할 수 없다.")
  void invalidUpdateTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser2")
        .content("두번째 민원")
        .processor("fing9")
        .reply("두번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId(), normalUser.getRoles());
    ComplaintUpdateRequest request = ComplaintUpdateRequest.builder()
        .id(2L)
        .content("다른 사람의 두번째 민원 수정")
        .complaintType(ComplaintType.NEW_FUNCTION)
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintRequesterController
          .update(request, refresh_token);
    }).isInstanceOf(AccessDeniedException.class);
  }
   */

  @Test
  @DisplayName("관리자는 다른 유저의 민원을 수정할 수 있다.")
  void adminUpdateTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId(), adminUser.getRoles());
    ComplaintUpdateRequest request = ComplaintUpdateRequest.builder()
        .id(1L)
        .content("첫번째 민원 수정")
        .complaintType(ComplaintType.NEW_FUNCTION)
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when
    try {
      response = testContainer.complaintRequesterController
          .update(request, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Optional<Complaint> complaint = testContainer.complaintRepository.findById(1L);

    // then
    assertThat(response.getBody().get("type")).isEqualTo(HttpStatus.OK.getReasonPhrase());
    assertThat(response.getBody().get("code")).isEqualTo("200");
    assertThat(response.getBody().get("message")).isEqualTo("민원을 성공적으로 수정했습니다.");
    assertThat(complaint.isPresent()).isEqualTo(true);
    assertThat(complaint.get().getContent()).isEqualTo(request.getContent());
    assertThat(complaint.get().getComplaintType()).isEqualTo(ComplaintType.NEW_FUNCTION);
  }

  @Test
  @DisplayName("유저는 민원을 삭제할 수 있다.")
  void deleteTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId(), normalUser.getRoles());
    ComplaintDeleteRequest request = ComplaintDeleteRequest.builder()
        .id(1L)
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when
    try {
      response = testContainer.complaintRequesterController
          .delete(request, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    List<Complaint> allComplaints = testContainer.complaintRepository.findAll();

    // then
    assertThat(response.getBody().get("type")).isEqualTo(HttpStatus.OK.getReasonPhrase());
    assertThat(response.getBody().get("code")).isEqualTo("200");
    assertThat(response.getBody().get("message")).isEqualTo("민원을 성공적으로 삭제했습니다.");
    assertThat(allComplaints.size()).isEqualTo(0);
  }

  // TODO: replace to WebTestClient test
  /*
  @Test
  @DisplayName("일반 유저는 타인의 민원을 삭제할 수 없다.")
  void invalidDeleteTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser2")
        .content("두번째 민원")
        .processor("fing9")
        .reply("두번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId(), normalUser.getRoles());
    ComplaintDeleteRequest request = ComplaintDeleteRequest.builder()
        .id(2L)
        .build();

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintRequesterController
          .delete(request, refresh_token);
    }).isInstanceOf(AccessDeniedException.class);
  }
   */

  @Test
  @DisplayName("관리자는 다른 유저의 민원을 삭제할 수 있다.")
  void adminDeleteTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId(), adminUser.getRoles());
    ComplaintDeleteRequest request = ComplaintDeleteRequest.builder()
        .id(1L)
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when
    try {
      response = testContainer.complaintRequesterController
          .delete(request, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Optional<Complaint> complaint = testContainer.complaintRepository.findById(1L);

    // then
    assertThat(response.getBody().get("type")).isEqualTo(HttpStatus.OK.getReasonPhrase());
    assertThat(response.getBody().get("code")).isEqualTo("200");
    assertThat(response.getBody().get("message")).isEqualTo("민원을 성공적으로 삭제했습니다.");
    assertThat(complaint.isEmpty()).isEqualTo(true);
  }

}
