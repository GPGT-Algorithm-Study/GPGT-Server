package com.randps.randomdefence.domain.user.service;

import static com.randps.randomdefence.global.component.parser.ConvertDifficulty.convertDifficulty;
import static com.randps.randomdefence.global.component.util.TimeUtil.getSomedayStart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.event.service.EventPointService;
import com.randps.randomdefence.domain.log.service.PointLogSaveService;
import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.scraping.service.ScrapingUserLogService;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserRandomStreak;
import com.randps.randomdefence.domain.user.domain.UserSolvedProblem;
import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
import com.randps.randomdefence.domain.user.dto.UserSolvedProblemPairDto;
import com.randps.randomdefence.domain.user.service.port.UserRandomStreakRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.domain.user.service.port.UserSolvedProblemRepository;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.parser.Parser;
import com.randps.randomdefence.global.component.util.TimeUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class UserSolvedProblemService {

  private final UserSolvedProblemRepository userSolvedProblemRepository;

  private final UserRandomStreakRepository userRandomStreakRepository;

  private final ProblemService problemService;

  private final PointLogSaveService pointLogSaveService;

  private final TeamService teamService;

  private final UserRepository userRepository;

  @Qualifier("bojParserToUse")
  private final Parser bojParser;

  private final UserStatisticsService userStatisticsService;

  private final UserAlreadySolvedService userAlreadySolvedService;

  private final EventPointService eventPointService;

  private final ScrapingUserLogService scrapingUserLogService;

  private final TimeUtil timeUtil;

  /*
   * 유저가 그동안 푼 모든 문제의 정보를 가져온다.
   */
  public List<SolvedProblemDto> findAllUserSolvedProblem(String bojHandle) {
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);
    List<SolvedProblemDto> solvedProblems = new ArrayList<>();

    for (UserSolvedProblem problem : userSolvedProblems) {
      SolvedProblemDto solvedProblemDto = problem.toDto();
      ProblemDto problemDto = problemService.findProblem(solvedProblemDto.getProblemId());
      solvedProblemDto.setTier(problemDto.getLevel());
      solvedProblemDto.setTags(problemDto.getTags());
      solvedProblemDto.setPoint(problemDto.getLevel());
      solvedProblemDto.setLanguage(problem.getLanguage());
      solvedProblems.add(solvedProblemDto);
    }

    return solvedProblems;
  }

  /*
   * 오늘 유저가 푼 모든 문제의 정보를 가져온다.
   */
  public List<SolvedProblemDto> findAllTodayUserSolvedProblem(String bojHandle) {
    // 오늘의 기준을 만든다.
    LocalDateTime startOfDateTime = timeUtil.getToday();

    // 데이터를 DB에서 가져온다.
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);
    List<SolvedProblemDto> solvedProblems = new ArrayList<>();

    // DB문제의 푼 날짜를 비교해서 오늘 푼 문제만 넣는다.
    for (UserSolvedProblem problem : userSolvedProblems) {
      LocalDateTime target = LocalDateTime.of(
          Integer.parseInt(problem.getDateTime().substring(0, 4)),
          Integer.parseInt(problem.getDateTime().substring(5, 7)),
          Integer.parseInt(problem.getDateTime().substring(8, 10)),
          Integer.parseInt(problem.getDateTime().substring(11, 13)),
          Integer.parseInt(problem.getDateTime().substring(14, 16)),
          Integer.parseInt(problem.getDateTime().substring(18)), 0);

      if (startOfDateTime.isBefore(target)) {
        SolvedProblemDto solvedProblemDto = problem.toDto();
        ProblemDto problemDto = problemService.findProblem(solvedProblemDto.getProblemId());
        solvedProblemDto.setTier(problemDto.getLevel());
        solvedProblemDto.setTags(problemDto.getTags());
        solvedProblemDto.setLanguage(problem.getLanguage());
        solvedProblemDto.setPoint(problemDto.getLevel());
        solvedProblems.add(solvedProblemDto);
      }
    }

    return solvedProblems;
  }

  /*
   * 특정 날에 유저가 푼 모든 문제의 정보를 가져온다.
   */
  public List<SolvedProblemDto> findAllSomedayUserSolvedProblem(String bojHandle,
      LocalDateTime someday) {
    // 요일 기준을 만든다.
    LocalDateTime startOfDateTime = getSomedayStart(someday);
    LocalDateTime endOfDateTime = startOfDateTime.plusDays(1);

    // 데이터를 DB에서 가져온다.
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);
    List<SolvedProblemDto> solvedProblems = new ArrayList<>();

    // DB문제의 푼 날짜를 비교해서 오늘 푼 문제만 넣는다.
    for (UserSolvedProblem problem : userSolvedProblems) {
      LocalDateTime target = LocalDateTime.of(
          Integer.parseInt(problem.getDateTime().substring(0, 4)),
          Integer.parseInt(problem.getDateTime().substring(5, 7)),
          Integer.parseInt(problem.getDateTime().substring(8, 10)),
          Integer.parseInt(problem.getDateTime().substring(11, 13)),
          Integer.parseInt(problem.getDateTime().substring(14, 16)),
          Integer.parseInt(problem.getDateTime().substring(18)), 0);

      System.out.println("target : " + target);
      if (target.isAfter(startOfDateTime) && target.isBefore(endOfDateTime)) {
        SolvedProblemDto solvedProblemDto = problem.toDto();
        ProblemDto problemDto = problemService.findProblem(solvedProblemDto.getProblemId());
        solvedProblemDto.setTier(problemDto.getLevel());
        solvedProblemDto.setTags(problemDto.getTags());
        solvedProblemDto.setLanguage(problem.getLanguage());
        solvedProblemDto.setPoint(problemDto.getLevel());
        solvedProblems.add(solvedProblemDto);
      }
    }

    return solvedProblems;
  }

  /*
   * 오늘 모든 유저가 푼 모든 문제의 정보를 가져온다.
   */
  @Transactional
  public List<UserSolvedProblemPairDto> findAllTodayUserSolvedProblemAll() {
    // 오늘의 기준을 만든다.
    LocalDateTime startOfDateTime = timeUtil.getToday();

    // 모든 유저 조회
    List<User> users = userRepository.findAll();
    List<UserSolvedProblemPairDto> userSolvedProblemPairDtos = new ArrayList<>();

    for (User user : users) {
      // 데이터를 DB에서 가져온다.
      List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
          user.getBojHandle());
      List<SolvedProblemDto> solvedProblems = new ArrayList<>();

      // DB문제의 푼 날짜를 비교해서 오늘 푼 문제만 넣는다.
      for (UserSolvedProblem problem : userSolvedProblems) {
        LocalDateTime target = LocalDateTime.of(
            Integer.parseInt(problem.getDateTime().substring(0, 4)),
            Integer.parseInt(problem.getDateTime().substring(5, 7)),
            Integer.parseInt(problem.getDateTime().substring(8, 10)),
            Integer.parseInt(problem.getDateTime().substring(11, 13)),
            Integer.parseInt(problem.getDateTime().substring(14, 16)),
            Integer.parseInt(problem.getDateTime().substring(18)), 0);

        if (startOfDateTime.isBefore(target)) {
          SolvedProblemDto solvedProblemDto = problem.toDto();
          ProblemDto problemDto = problemService.findProblem(solvedProblemDto.getProblemId());
          solvedProblemDto.setTier(problemDto.getLevel());
          solvedProblemDto.setTags(problemDto.getTags());
          solvedProblemDto.setPoint(problemDto.getPoint());
          solvedProblemDto.setLanguage(problem.getLanguage());
          solvedProblems.add(solvedProblemDto);
        }
      }

      userSolvedProblemPairDtos.add(
          UserSolvedProblemPairDto.builder().bojHandle(user.getBojHandle())
              .solvedProblemList(solvedProblems).build());
    }

    return userSolvedProblemPairDtos;
  }

  /*
   * 유저가 오늘 푼 문제를 크롤링 후, DB에 저장한다.
   */
  @Transactional
  public void crawlTodaySolvedProblem(String bojHandle) throws JsonProcessingException {
    LocalDateTime today = timeUtil.getToday();
    if (!scrapingUserLogService.isTodayScraping(bojHandle)) {
      today = today.minusDays(1);
    }
    bojParser.setStartOfActiveDay(today);
    List<Object> problems = bojParser.getSolvedProblemList(bojHandle);

    // 중복 제거를 위해 기존의 푼 문제 목록을 가져온다.
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);

    // 모든 스크래핑 한 데이터를 푼 문제 목록에 추가한다.
    for (Object problem : problems) {
      BojProblemPair pair = (BojProblemPair) problem;
      UserSolvedProblem userSolvedProblem = UserSolvedProblem.builder().bojHandle(bojHandle)
          .problemId(pair.getProblemId()).title(pair.getTitle()).dateTime(pair.getDateTime())
          .language(pair.getLanguage()).build();
      // 중복 제거 로직
      boolean isAlreadyExist = false;
      for (UserSolvedProblem alreadySolvedProblem : userSolvedProblems) {
        if (alreadySolvedProblem.getProblemId().equals(userSolvedProblem.getProblemId())) {
          isAlreadyExist = true;
          break;
        }
      }
      // 중복이 없다면 저장한다.
      if (!isAlreadyExist && !userAlreadySolvedService.isSolved(bojHandle,
          userSolvedProblem.getProblemId())) {
        // 문제의 포인트만큼 유저의 포인트를 추가한다.
        ProblemDto pb = problemService.findProblem(userSolvedProblem.getProblemId());
        User user = userRepository.findByBojHandle(bojHandle)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        UserRandomStreak userRandomStreak = userRandomStreakRepository.findByBojHandle(bojHandle)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스트릭입니다."));
        // 랜덤 스트릭 문제라면 따로 포인트를 부여한다.
        if (!userRandomStreak.getTodayRandomProblemId().equals(pb.getProblemId())) {
          // 일반 문제의 포인트 부여
          user.increasePoint(pb.getPoint());
          pointLogSaveService.savePointLog(user.getBojHandle(), pb.getPoint(),
              pb.getPoint() + " points are earned by solving problem " + pb.getProblemId()
                  .toString() + " : " + "\"" + pb.getTitleKo() + "\"" + " level - "
                  + convertDifficulty(pb.getLevel()), true);

          // 이벤트를 적용한다
          eventPointService.applyEventPoint(user.getBojHandle(), pb.getPoint());

          // 팀의 점수를 올린다. (일반 문제)
          teamService.increaseTeamScore(user.getTeam(), pb.getPoint());
          // 유저 통계를 반영한다. (일반 문제)
          userStatisticsService.updateByDto(user.getBojHandle(), pb, pb.getPoint());
        }

        userSolvedProblems.add(userSolvedProblem);
      }
    }

    userSolvedProblemRepository.saveAll(userSolvedProblems);
    scrapingUserLogService.saveScrapingUserLog(bojHandle);
  }

  /*
   * 모든 유저가 오늘 푼 문제를 크롤링 후, DB에 저장한다.
   */
  @Transactional
  public void crawlTodaySolvedProblemAll() throws JsonProcessingException {
    List<User> users = userRepository.findAll();

    for (User user : users) {
      crawlTodaySolvedProblem(user.getBojHandle());
    }
  }

  /*
   * 오늘 유저가 풀었는지 여부를 반환한다.
   */
  @Transactional
  public Boolean isTodaySolved(String bojHandle) {
    // 오늘의 기준을 만든다.
    LocalDateTime startOfDateTime = timeUtil.getToday();

    // 데이터를 DB에서 가져온다.
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);

    // DB문제의 푼 날짜를 비교해서 오늘 푼 문제가 한개라도 있다면 true를 반환한다.
    for (UserSolvedProblem problem : userSolvedProblems) {
      LocalDateTime target = LocalDateTime.of(
          Integer.parseInt(problem.getDateTime().substring(0, 4)),
          Integer.parseInt(problem.getDateTime().substring(5, 7)),
          Integer.parseInt(problem.getDateTime().substring(8, 10)),
          Integer.parseInt(problem.getDateTime().substring(11, 13)),
          Integer.parseInt(problem.getDateTime().substring(14, 16)),
          Integer.parseInt(problem.getDateTime().substring(18)), 0);

      if (startOfDateTime.isBefore(target)) {
        return true;
      }
    }

    return false;
  }

  /*
   * 어제 유저가 문제를 풀었는지 여부를 반환한다.
   */
  @Transactional
  public Boolean isYesterdaySolved(String bojHandle) {
    // 어제의 기준을 만든다.
    LocalDateTime startOfDateTime = timeUtil.getYesterdayStart();
    LocalDateTime endOfDateTime = timeUtil.getYesterdayEnd();

    // 데이터를 DB에서 가져온다.
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);

    // DB문제의 푼 날짜를 비교해서 어제 푼 문제가 한개라도 있다면 true를 반환한다.
    for (UserSolvedProblem problem : userSolvedProblems) {
      LocalDateTime target = LocalDateTime.of(
          Integer.parseInt(problem.getDateTime().substring(0, 4)),
          Integer.parseInt(problem.getDateTime().substring(5, 7)),
          Integer.parseInt(problem.getDateTime().substring(8, 10)),
          Integer.parseInt(problem.getDateTime().substring(11, 13)),
          Integer.parseInt(problem.getDateTime().substring(14, 16)),
          Integer.parseInt(problem.getDateTime().substring(18)), 0);
      if (target.isAfter(startOfDateTime) && target.isBefore(endOfDateTime)) {
        return true;
      }
    }
    return false;
  }

  /*
   * 오늘 몇 문제 풀었는지 개수를 반환한다.
   */
  @Transactional
  public Integer getTodaySolvedProblemCount(String bojHandle) {
    // 오늘의 기준을 만든다.
    LocalDateTime startOfDateTime = timeUtil.getToday();

    // 데이터를 DB에서 가져온다.
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);

    Integer cnt = 0;
    // DB문제의 푼 날짜를 비교해서 오늘 푼 문제의 개수를 새고 반환한다.
    for (UserSolvedProblem problem : userSolvedProblems) {
      LocalDateTime target = LocalDateTime.of(
          Integer.parseInt(problem.getDateTime().substring(0, 4)),
          Integer.parseInt(problem.getDateTime().substring(5, 7)),
          Integer.parseInt(problem.getDateTime().substring(8, 10)),
          Integer.parseInt(problem.getDateTime().substring(11, 13)),
          Integer.parseInt(problem.getDateTime().substring(14, 16)),
          Integer.parseInt(problem.getDateTime().substring(18)), 0);

      if (startOfDateTime.isBefore(target)) {
        cnt++;
      }
    }

    return cnt;
  }

  /*
   * 어제 몇 문제 풀었는지 개수를 반환한다.
   */
  @Transactional
  public Integer getYesterdaySolvedProblemCount(String bojHandle) {
    // 어제의 기준을 만든다.
    LocalDateTime startOfDateTime = timeUtil.getYesterdayStart();
    LocalDateTime endOfDateTime = timeUtil.getYesterdayEnd();

    // 데이터를 DB에서 가져온다.
    List<UserSolvedProblem> userSolvedProblems = userSolvedProblemRepository.findAllByBojHandle(
        bojHandle);

    Integer cnt = 0;
    // DB문제의 푼 날짜를 비교해서 어제 푼 문제의 개수를 새고 반환한다.
    for (UserSolvedProblem problem : userSolvedProblems) {
      LocalDateTime target = LocalDateTime.of(
          Integer.parseInt(problem.getDateTime().substring(0, 4)),
          Integer.parseInt(problem.getDateTime().substring(5, 7)),
          Integer.parseInt(problem.getDateTime().substring(8, 10)),
          Integer.parseInt(problem.getDateTime().substring(11, 13)),
          Integer.parseInt(problem.getDateTime().substring(14, 16)),
          Integer.parseInt(problem.getDateTime().substring(18)), 0);

      if (target.isAfter(startOfDateTime) && target.isBefore(endOfDateTime)) {
        cnt++;
      }
    }

    return cnt;
  }

  /*
   * 유저의 모든 푼 문제를 삭제한다.
   */
  @Transactional
  public void deleteAllByBojHandle(String bojHandle) {
    userSolvedProblemRepository.deleteAllByBojHandle(bojHandle);
  }

}
