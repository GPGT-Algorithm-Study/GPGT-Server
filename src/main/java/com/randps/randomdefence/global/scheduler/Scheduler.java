package com.randps.randomdefence.global.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.notify.service.AutoNotifyService;
import com.randps.randomdefence.domain.statistics.service.UserStatisticsService;
import com.randps.randomdefence.domain.team.service.TeamService;
import com.randps.randomdefence.domain.team.service.TeamSettingService;
import com.randps.randomdefence.domain.user.service.UserGrassService;
import com.randps.randomdefence.domain.user.service.UserInfoService;
import com.randps.randomdefence.domain.user.service.UserRandomStreakService;
import com.randps.randomdefence.domain.user.service.UserSolvedProblemService;
import com.randps.randomdefence.global.aws.s3.service.S3BatchService;
import com.randps.randomdefence.global.component.util.CrawlingLock;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Builder
@RequiredArgsConstructor
@SpringBootApplication
@EnableScheduling
public class Scheduler {

  private final AutoNotifyService autoNotifyService;

  private final UserInfoService userInfoService;

  private final UserSolvedProblemService userSolvedProblemService;

  private final UserRandomStreakService userRandomStreakService;

  private final UserGrassService userGrassService;

  private final UserStatisticsService userStatisticsService;

  private final TeamSettingService teamSettingService;

  private final TeamService teamService;

  private final S3BatchService s3BatchService;

  private final CrawlingLock crawlingLock;

  /*
   * 정해진 시간마다 실행되는 스크래핑 메서드 (매 20분 간격)
   */
  @Transactional
  @Scheduled(cron = "0 0/20 * * * *")
  public void everyTermJob() throws JsonProcessingException {
    log.info("[Every Term Crawling Start]");
    crawlingLock.lock(); // 크롤링 중복 방지를 위한 락
    try {
      log.info("모든 유저의 맞았습니다를 크롤링해서 해결한 문제 DB를 업데이트 한다.");
      userSolvedProblemService.crawlTodaySolvedProblemAll(); // 모든 유저의 맞았습니다를 크롤링해서 해결한 문제 DB를 업데이트한다.
      log.info("[SUCCESS]");
      log.info("모든 유저의 프로필 정보를 크롤링해서 DB를 업데이트한다.");
      userInfoService.crawlUserInfoAll(); // 모든 유저의 프로필 정보를 크롤링해서 DB를 업데이트한다.
      log.info("[SUCCESS]");
      log.info("모든 유저의 오늘의 추첨 랜덤 문제 풀었는지 여부를 체크하고 DB를 업데이트한다.");
      userRandomStreakService.solvedCheckAll(); // 모든 유저의 오늘의 추첨 랜덤 문제 풀었는지 여부를 체크하고 DB를 업데이트한다.
      log.info("[SUCCESS]");
      log.info("모든 유저의 문제 풀었는지 여부를 체크해서 저장한다.");
      userInfoService.updateAllUserInfo(); // 모든 유저의 문제 풀었는지 여부를 체크해서 저장한다.
      log.info("[SUCCESS]");
      log.info("게시글과 이어지지 않고 기준시간(6시간)이상 지난 모든 이미지를 삭제한다.");
      s3BatchService.deleteDetachedImages(); // 게시글과 이어지지 않고 기준시간(6시간)이상 지난 모든 이미지를 삭제한다.
      log.info("[SUCCESS]");
    } finally {
      crawlingLock.unlock(); // 크롤링 중복 방지를 위한 락 해제
      log.info("[Crawling Done]");
    }
  }

  /*
   * 정해진 시간마다 실행되는 스크래핑 메서드 (하루 간격, 매일 새벽 6시 25분)
   */
  @Transactional
  @Scheduled(cron = "0 25 6 * * *")
  public void everyDayTermJob() throws JsonProcessingException {
    log.info("[Every Day Term Start]");
    log.info("모든 유저의 오늘 잔디를 생성한다.");
    userGrassService.makeTodayGrassAll(); // 모든 유저의 오늘 잔디를 생성한다.
    log.info("[SUCCESS]");
    log.info("모든 유저의 랜덤 문제를 1문제를 뽑아 저장한다.");
    userRandomStreakService.makeUpUserRandomProblemAll(); // 모든 유저의 랜덤 문제를 1문제를 뽑아 저장한다.
    log.info("[SUCCESS]");
    log.info("모든 유저에 대해 유저의 전일 문제가 풀리지 않았다면 랜덤 스트릭을 끊는다.");
    userRandomStreakService.streakCheckAll(); // 모든 유저에 대해 유저의 전일 문제가 풀리지 않았다면 랜덤 스트릭을 끊는다.
    log.info("[SUCCESS]");
    log.info("모든 유저에 대해 전 일 스트릭이 끊겼다면(랜덤 스트릭이 아닌 Solvedac 스트릭) 경고를 1회 올린다.");
    userInfoService.checkAllUserSolvedStreak(); // 모든 유저에 대해 전 일 스트릭이 끊겼다면(랜덤 스트릭이 아닌 Solvedac 스트릭) 경고를 1회 올린다.
    log.info("[SUCCESS]");
    log.info("모든 유저의 일간 통계를 초기화한다.");
    userStatisticsService.initAllDailyStat(); // 모든 유저의 일간 통계를 초기화한다.
    log.info("[SUCCESS]");
    log.info("[Done]");
  }

  /*
   * 주간 초기화 메서드 (매 주 월요일 새벽 6시 26분)
   */
  @Transactional
  @Scheduled(cron = "0 26 6 * * 1")
  public void weekInitJob() {
    log.info("[Week Initialize Start]");
    log.info("모든 유저의 주간 통계를 초기화한다.");
    userStatisticsService.initAllWeeklyStat(); // 모든 유저의 주간 통계를 초기화한다.
    log.info("[SUCCESS]");
    log.info("승리 팀에게 승리 포인트 지급");
    teamService.weeklyTeamPointDistribution(); // 승리 팀에게 승리 포인트 지급
    log.info("[SUCCESS]");
    log.info("팀 포인트 주간 초기화");
    teamSettingService.initWeekly(); // 팀 포인트 주간 초기화
    log.info("[SUCCESS]");
    log.info("모든 유저 팀 할당");
    teamSettingService.setUsers(); // 모든 유저 팀 할당
    log.info("[SUCCESS]");
    log.info("[Done]");
  }

  /*
   * 매 주, 주말에 경고 알림 발행 메서드 (매 주 토, 일요일 새벽 6시 30분)
   */
  @Transactional
  @Scheduled(cron = "0 30 6 * * SAT,SUN")
  public void weekendWarningJob() {
    log.info("[Weekend Warning Start]");
    log.info("경고가 3회인 유저에게 경고 알림을 발행한다.");
    autoNotifyService.weekendWarningNotify(); // 경고가 3회인 유저에게 경고 알림을 발행한다.
    log.info("[SUCCESS]");
    log.info("[Done]");
  }

}
