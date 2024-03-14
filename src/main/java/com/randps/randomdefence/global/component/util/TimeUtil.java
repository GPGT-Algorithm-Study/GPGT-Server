package com.randps.randomdefence.global.component.util;

import static com.randps.randomdefence.global.component.crawler.BojWebCrawler.is6AmAfter;

import java.time.LocalDateTime;

public class TimeUtil {

  /*
   * 6시를 기준으로한 오늘의 시작 시간을 가져온다.
   */
  public static LocalDateTime getToday() {
    // 오늘의 기준을 만든다.
    LocalDateTime now = LocalDateTime.now();
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
  public static LocalDateTime getYesterdayStart() {
    // 어제의 기준을 만든다.
    LocalDateTime now = LocalDateTime.now();
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
  public static LocalDateTime getYesterdayEnd() {
    return getToday();
  }

}