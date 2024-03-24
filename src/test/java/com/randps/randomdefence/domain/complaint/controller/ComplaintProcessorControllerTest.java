package com.randps.randomdefence.domain.complaint.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.dto.ComplaintDetailResponse;
import com.randps.randomdefence.domain.complaint.dto.ComplaintProcessorUpdateRequest;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
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

public class ComplaintProcessorControllerTest {

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
  @DisplayName("관리자는 모든 민원을 조회할 수 있다.")
  void getAllComplaintsTest() {
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
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId());
    List<ComplaintDetailResponse> allComplaints = new ArrayList<>();

    // when
    try {
      allComplaints = testContainer.complaintProcessorController
          .searchAll(refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // then
    assertThat(allComplaints.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("관리자가 아닌 유저는 모든 민원을 조회하려하면 예외가 발생한다.")
  void invalidGetAllComplaintsTest() {
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
        normalUser.getNotionId());

    // when & then
    assertThatThrownBy(() -> {
          testContainer.complaintProcessorController.searchAll(refresh_token);
        }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("관리자는 모든 민원을 민원 번호를 역순으로 정렬한 상태로 조회할 수 있다.")
  void searchAllSortTest() {
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
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId());
    List<ComplaintDetailResponse> allComplaints = new ArrayList<>();

    // when
    try {
      allComplaints = testContainer.complaintProcessorController
          .searchAllSort(refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // then
    assertThat(allComplaints.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("일반 유저가 민원 번호로 정렬한 순서로 모든 민원을 조회하면 예외가 발생한다.")
  void invalidSearchAllSortTest() {
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
        normalUser.getNotionId());

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchAllSort(refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("관리자는 특정 요청자의 모든 민원을 조회할 수 있다.")
  void searchRequesterTest() {
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
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId());
    List<ComplaintDetailResponse> allComplaints = new ArrayList<>();

    // when
    try {
      allComplaints = testContainer.complaintProcessorController
          .searchRequester("normalUser", refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // then
    assertThat(allComplaints.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("일반 유저가 다른 유저의 민원을 조회하면 예외가 발생한다.")
  void invalidSearchRequesterTest() {
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
        normalUser.getNotionId());

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchRequester("normalUser2", refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("관리자는 특정 민원 처리자의 모든 민원을 조회할 수 있다.")
  void searchProcessorTest() {
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
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId());
    List<ComplaintDetailResponse> allComplaints = new ArrayList<>();

    // when
    try {
      allComplaints = testContainer.complaintProcessorController
          .searchProcessor("fing9", refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // then
    assertThat(allComplaints.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("일반 유저가 특정 민원 처리자가 담당한 민원을 조회하면 예외가 발생한다.")
  void invalidSearchProcessorTest() {
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
        normalUser.getNotionId());

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchProcessor("fing9", refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("관리자는 특정 민원 유형의 모든 민원을 조회할 수 있다.")
  void searchTypeTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser")
        .content("두번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("두번째 민원 답변")
        .build();
    Complaint newComplaint3 = Complaint.builder()
        .requester("normalUser2")
        .content("세번째 민원")
        .complaintType(ComplaintType.ETC)
        .processor("fing9")
        .reply("세번째 민원 답변")
        .build();
    Complaint newComplaint4 = Complaint.builder()
        .requester("normalUser2")
        .content("네번째 민원")
        .complaintType(ComplaintType.NEW_FUNCTION)
        .processType(ProcessType.WAITING)
        .processor("fing9")
        .reply("네번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    testContainer.complaintRepository.save(newComplaint3);
    testContainer.complaintRepository.save(newComplaint4);
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId());
    List<ComplaintDetailResponse> allComplaints = new ArrayList<>();

    // when
    try {
      allComplaints = testContainer.complaintProcessorController
          .searchType(ComplaintType.BUG, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // then
    assertThat(allComplaints.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("일반 유저가 특정 민원 유형의 모든 민원을 조회하면 예외가 발생한다.")
  void invalidSearchTypeTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser")
        .content("두번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("두번째 민원 답변")
        .build();
    Complaint newComplaint3 = Complaint.builder()
        .requester("normalUser2")
        .content("세번째 민원")
        .complaintType(ComplaintType.ETC)
        .processor("fing9")
        .reply("세번째 민원 답변")
        .build();
    Complaint newComplaint4 = Complaint.builder()
        .requester("normalUser2")
        .content("네번째 민원")
        .complaintType(ComplaintType.NEW_FUNCTION)
        .processType(ProcessType.WAITING)
        .processor("fing9")
        .reply("네번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    testContainer.complaintRepository.save(newComplaint3);
    testContainer.complaintRepository.save(newComplaint4);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId());

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchType(ComplaintType.BUG, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchType(ComplaintType.ETC, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchType(ComplaintType.NEW_FUNCTION, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchType(ComplaintType.PROBLEM, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
  }


  @Test
  @DisplayName("관리자는 특정 처리 유형의 모든 민원을 조회할 수 있다.")
  void searchProcessTypeTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser")
        .content("두번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("두번째 민원 답변")
        .build();
    Complaint newComplaint3 = Complaint.builder()
        .requester("normalUser2")
        .content("세번째 민원")
        .complaintType(ComplaintType.ETC)
        .processor("fing9")
        .reply("세번째 민원 답변")
        .build();
    Complaint newComplaint4 = Complaint.builder()
        .requester("normalUser2")
        .content("네번째 민원")
        .complaintType(ComplaintType.NEW_FUNCTION)
        .processType(ProcessType.WAITING)
        .processor("fing9")
        .reply("네번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    testContainer.complaintRepository.save(newComplaint3);
    testContainer.complaintRepository.save(newComplaint4);
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId());
    List<ComplaintDetailResponse> allComplaints = new ArrayList<>();

    // when
    try {
      allComplaints = testContainer.complaintProcessorController
          .searchType(ComplaintType.BUG, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // then
    assertThat(allComplaints.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("일반 유저가 특정 처리 유형의 모든 민원을 조회하면 예외가 발생한다.")
  void invalidSearchProcessTypeTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser")
        .content("두번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("두번째 민원 답변")
        .build();
    Complaint newComplaint3 = Complaint.builder()
        .requester("normalUser2")
        .content("세번째 민원")
        .complaintType(ComplaintType.ETC)
        .processor("fing9")
        .reply("세번째 민원 답변")
        .build();
    Complaint newComplaint4 = Complaint.builder()
        .requester("normalUser2")
        .content("네번째 민원")
        .complaintType(ComplaintType.NEW_FUNCTION)
        .processType(ProcessType.WAITING)
        .processor("fing9")
        .reply("네번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    testContainer.complaintRepository.save(newComplaint3);
    testContainer.complaintRepository.save(newComplaint4);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId());

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchProcessType(ProcessType.WAITING, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchProcessType(ProcessType.PROCESSING, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.searchProcessType(ProcessType.DONE, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("관리자는 특정 민원의 상태를 바꿀 수 있다.")
  void changeProcessTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser2")
        .content("두번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .processType(ProcessType.WAITING)
        .reply("두번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    User adminUser = testContainer.userRepository.findByBojHandle("fing9").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(adminUser.getId(), adminUser.getBojHandle(),
        adminUser.getNotionId());
    ComplaintProcessorUpdateRequest complaintProcessorUpdateRequest = ComplaintProcessorUpdateRequest.builder()
        .id(1L)
        .processType(ProcessType.PROCESSING)
        .processor("fing9")
        .reply("첫번째 민원 답변 수정")
        .build();
    ResponseEntity<Map<String, String>> response = null;

    // when
    try {
      response = testContainer.complaintProcessorController
          .changeProcess(complaintProcessorUpdateRequest, refresh_token);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    Complaint ret = testContainer.complaintRepository.findById(1L).get();

    // then
    assertThat(ret.getProcessType()).isEqualTo(ProcessType.PROCESSING);
    assertThat(ret.getProcessor()).isEqualTo("fing9");
    assertThat(ret.getReply()).isEqualTo("첫번째 민원 답변 수정");
    assertThat(response.getBody().get("type")).isEqualTo(HttpStatus.OK.getReasonPhrase());
    assertThat(response.getBody().get("code")).isEqualTo("200");
    assertThat(response.getBody().get("message")).isEqualTo("민원의 상태를 성공적으로 수정했습니다.");
  }

  @Test
  @DisplayName("일반 유저가 민원의 상태를 바꾸면 예외가 발생한다.")
  void invalidChangeProcessTest() {
    // given
    Complaint newComplaint1 = Complaint.builder()
        .requester("normalUser")
        .content("첫번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .reply("첫번째 민원 답변")
        .build();
    Complaint newComplaint2 = Complaint.builder()
        .requester("normalUser2")
        .content("두번째 민원")
        .complaintType(ComplaintType.BUG)
        .processor("fing9")
        .processType(ProcessType.WAITING)
        .reply("두번째 민원 답변")
        .build();
    testContainer.complaintRepository.save(newComplaint1);
    testContainer.complaintRepository.save(newComplaint2);
    User normalUser = testContainer.userRepository.findByBojHandle("normalUser").get();
    String refresh_token = testContainer.jwtProvider.generateJwtToken(normalUser.getId(), normalUser.getBojHandle(),
        normalUser.getNotionId());
    ComplaintProcessorUpdateRequest complaintProcessorUpdateRequest = ComplaintProcessorUpdateRequest.builder()
        .id(1L)
        .processType(ProcessType.PROCESSING)
        .processor("fing9")
        .reply("첫번째 민원 답변 수정")
        .build();

    // when & then
    assertThatThrownBy(() -> {
      testContainer.complaintProcessorController.changeProcess(complaintProcessorUpdateRequest, refresh_token);
    }).isInstanceOf(IllegalArgumentException.class);
  }
}
