package com.randps.randomdefence.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.domain.log.service.WarningLogSaveService;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.notify.service.NotifyService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.global.component.parser.Parser;
import com.randps.randomdefence.global.component.parser.SolvedacParser;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class UserInfoService {

  private final UserRepository userRepository;

  private final UserRandomStreakService userRandomStreakService;

  private final WarningLogSaveService warningLogSaveService;

  private final UserSolvedProblemService userSolvedProblemService;

  private final SolvedacParser solvedacParser;

  private final NotifyService notifyService;

  @Qualifier("bojParserToUse")
  private final Parser bojParser;

  /*
   * 유저의 프로필 정보를 불러온다.
   */
  public UserInfoResponse getInfo(String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    UserRandomStreak userRandomStreak = userRandomStreakService.findUserRandomStreak(bojHandle);

    return user.toUserInfoResponse(userRandomStreak.getMaxRandomStreak());
  }

  /**
   * 모든 유저의 프로필 정보를 불러온다. (Querydsl)
   */
  public List<UserInfoResponse> getAllInfo() {

    return userRepository.findAllUserResponse();
  }

  /*
   * 특정 유저가 오늘 문제를 풀었는지 여부를 갱신한다.
   */
  @Transactional
  public void updateUserInfo(String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    
    // 오늘 문제 푼 것을 축하하는 알림을 발행한다.
    Boolean isTodaySolved = userSolvedProblemService.isTodaySolved(user.getBojHandle());
    if (!user.getIsTodaySolved() && isTodaySolved) {
      notifyService.systemPublish(user.getBojHandle(), "😊🥳 오늘도 문제를 해결하셨네요! 정말 정말 잘 했어요!",
          NotifyType.SYSTEM, null);
    }

    user.setIsTodaySolved(isTodaySolved);
    user.setTodaySolvedProblemCount(
        userSolvedProblemService.getTodaySolvedProblemCount(user.getBojHandle()));
    userRepository.save(user);
  }

  /*
   * 모든 유저가 오늘 문제를 풀었는지 여부를 갱신한다.
   */
  @Transactional
  public void updateAllUserInfo() {
    List<User> users = userRepository.findAll();

    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);

      // 오늘 문제 푼 것을 축하하는 알림을 발행한다.
      Boolean isTodaySolved = userSolvedProblemService.isTodaySolved(user.getBojHandle());
      if (!user.getIsTodaySolved() && isTodaySolved) {
        notifyService.systemPublish(user.getBojHandle(), "😊🥳 오늘도 문제를 해결하셨네요! 정말 정말 잘 했어요!",
            NotifyType.SYSTEM, null);
      }

      user.setIsTodaySolved(isTodaySolved);
      user.setTodaySolvedProblemCount(
          userSolvedProblemService.getTodaySolvedProblemCount(user.getBojHandle()));
      userRepository.save(user);
    }
  }


  /*
   * 유저의 프로필 정보를 크롤링 후, DB에 저장한다.
   */
  @Transactional
  public void crawlUserInfo(String bojHandle) throws JsonProcessingException {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

    user.setScrapingUserInfo(solvedacParser.getSolvedUserInfo(user.getBojHandle()));
    user.setIsTodaySolved(userSolvedProblemService.isTodaySolved(user.getBojHandle()));
    user.setTodaySolvedProblemCount(
        userSolvedProblemService.getTodaySolvedProblemCount(user.getBojHandle()));
    userRepository.save(user);
  }

  /*
   * 모든 유저의 프로필 정보를 크롤링 후, DB에 저장한다.
   */
  @Transactional
  public void crawlUserInfoAll() throws JsonProcessingException {
    List<User> users = userRepository.findAll();

    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);

      user.setScrapingUserInfo(solvedacParser.getSolvedUserInfo(user.getBojHandle()));
      user.setIsTodaySolved(userSolvedProblemService.isTodaySolved(user.getBojHandle()));
      user.setTodaySolvedProblemCount(
          userSolvedProblemService.getTodaySolvedProblemCount(user.getBojHandle()));
      userRepository.save(user);
    }
  }

  /*
   * 모든 유저의 스트릭 끊김 여부를 확인 후, 스트릭이 끊겼다면 경고를 1 올린다. (Daily batch job 서버용)
   * 실행 시점이 다음 날이므로 어제의 문제를 확인한다.
   */
  @Transactional
  public void checkAllUserSolvedStreak() throws JsonProcessingException {
    List<User> users = userRepository.findAll();

    for (User user : users) {
      user.setScrapingUserInfo(solvedacParser.getSolvedUserInfo(user.getBojHandle()));
      user.setIsTodaySolved(userSolvedProblemService.isTodaySolved(user.getBojHandle()));
      user.setTodaySolvedProblemCount(
          userSolvedProblemService.getTodaySolvedProblemCount(user.getBojHandle()));
      userRepository.save(user);
      if (!userSolvedProblemService.isYesterdaySolved(user.getBojHandle())) {
        // 유저가 어제 문제를 풀었는지 여부를 갱신한다.
        user.checkYesterdayRandomSolvedNo();
        boolean isSuccess = user.increaseWarning();
        // 경고 로그를 저장한다.
        if (isSuccess) {
          warningLogSaveService.saveWarningLog(user.getBojHandle(), 1,
              "[" + user.getBojHandle() + "]" + "'s warnings increased by 1" + " - 사유: 스트릭 끊김 "
                  + "[" + (user.getWarning() - 1) + "->" + user.getWarning() + "]", true);
        }
        // 스트릭 끊김을 알리는 알림을 발행한다.
        notifyService.systemPublish(user.getBojHandle(), "문제를 풀지 않아, 경고가 부여됐습니다.",
            NotifyType.SYSTEM, null);

        userRepository.save(user);
      } else {
        // 유저가 어제 문제를 풀었는지 여부를 갱신한다.
        user.checkYesterdayRandomSolvedOk();
        userRepository.save(user);
      }
    }
  }

  /*
   * 유저의 프로필 정보를 불러온다. (직접 불러오기)
   */
  public JsonNode getInfoRaw(String bojHandle) throws JsonProcessingException {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

    return solvedacParser.crawlingUserInfo(bojHandle);
  }

  /*
   * 유저가 오늘 푼 문제 목록을 불러온다. (직접 불러오기)
   */
  public List<Object> getTodaySolvedRaw(String bojHandle) throws JsonProcessingException {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

    return bojParser.getSolvedProblemList(bojHandle);
  }

}
