package com.randps.randomdefence.global.component.util;

import static com.randps.randomdefence.global.component.crawler.BojWebCrawler.is6AmAfter;

import com.randps.randomdefence.global.component.util.port.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeUtil {

  private final Clock clock;

  /*
   * 6시를 기준으로한 오늘의 시작 시간을 가져온다.
   */
  public LocalDateTime getToday() {
    // 오늘의 기준을 만든다.
    LocalDateTime now = clock.now();
    LocalDateTime startOfDateTime;
    if (is6AmAfter(now.getHour())) {
      startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0,
          0);
    } else {
      now = now.minusDays(1);
      startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0,
          0);
    }
    return startOfDateTime;
  }

  /*
   * 6시를 기준으로한 어제의 시작 시간을 가져온다.
   */
  public LocalDateTime getYesterdayStart() {
    // 어제의 기준을 만든다.
    LocalDateTime now = clock.now();
    LocalDateTime startOfDateTime;
    if (is6AmAfter(now.getHour())) {
      now = now.minusDays(1);
      startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0,
          0);
    } else {
      now = now.minusDays(2);
      startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0,
          0);
    }
    return startOfDateTime;
  }

  /*
   * 6시를 기준으로한 어제의 끝 시간을 가져온다.
   */
  public LocalDateTime getYesterdayEnd() {
    return getToday();
  }

  /*
   * 6시를 기준으로한 특정 날짜의 시작 시간을 가져온다.
   */
  public static LocalDateTime getSomedayStart(LocalDateTime someday) {
    // 오늘의 기준을 만든다.
    LocalDateTime now = someday;
    LocalDateTime startOfDateTime;
    if (is6AmAfter(now.getHour())) {
      startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0,
          0);
    } else {
      now = now.minusDays(1);
      startOfDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 0,
          0);
    }
    return startOfDateTime;
  }

}
