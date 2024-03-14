package com.randps.randomdefence.domain.scraping.service;

import static com.randps.randomdefence.global.component.util.TimeUtil.getToday;

import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import com.randps.randomdefence.domain.scraping.service.mock.ScrapingUserLogRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@RequiredArgsConstructor
@Service
public class ScrapingUserLogService {

  private final ScrapingUserLogRepository scrapingUserLogRepository;

  @Transactional
  public Boolean isPossible(String bojHandle) {
    ScrapingUserLog userLog = scrapingUserLogRepository.findByBojHandle(bojHandle)
        .orElseGet(() -> ScrapingUserLog.builder().bojHandle(bojHandle).build());

    if (!userLog.isScrapingPossible()) {
      return false;
    }
    userLog.saveLastUserScrapingRequestTime(LocalDateTime.now());
    scrapingUserLogRepository.save(userLog);
    return true;
  }

  /*
   * 유저의 마지막 스크래핑 시간을 저장한다.
   */
  @Transactional
  public void saveScrapingUserLog(String bojHandle) {
    ScrapingUserLog userLog = scrapingUserLogRepository.findByBojHandle(bojHandle)
        .orElseGet(() -> ScrapingUserLog.builder().bojHandle(bojHandle).build());
    userLog.saveLastScrapingTime(LocalDateTime.now());
    scrapingUserLogRepository.save(userLog);
  }

  /*
   * 유저의 마지막 스크래핑 시간을 가져온다.
   */
  public LocalDateTime getLastScrapingTime(String bojHandle) {
    ScrapingUserLog userLog = scrapingUserLogRepository.findByBojHandle(bojHandle)
        .orElseGet(() -> ScrapingUserLog.builder().bojHandle(bojHandle).build());
    return userLog.getLastScrapingTime();
  }

  /*
   * 오늘 유저가 스크래핑을 한번이라도 한 적 있는지 여부를 반환한다.
   */
  public Boolean isTodayScraping(String bojHandle) {
    Optional<ScrapingUserLog> userLog = scrapingUserLogRepository.findByBojHandle(bojHandle);

    return userLog.map(scrapingUserLog -> scrapingUserLog.getLastScrapingTime().isAfter(getToday()))
        .orElse(false);

  }

}
