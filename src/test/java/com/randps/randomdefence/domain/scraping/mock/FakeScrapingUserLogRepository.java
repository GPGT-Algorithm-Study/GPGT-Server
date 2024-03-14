package com.randps.randomdefence.domain.scraping.mock;

import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import com.randps.randomdefence.domain.scraping.service.mock.ScrapingUserLogRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeScrapingUserLogRepository implements ScrapingUserLogRepository {

  private final List<ScrapingUserLog> data = new ArrayList<>();

  private Long autoIncreasingCount = 0L;


  @Override
  public Optional<ScrapingUserLog> findByBojHandle(String bojHandle) {
    return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).findAny();
  }

  @Override
  public ScrapingUserLog save(ScrapingUserLog userLog) {
    if (userLog.getId() == null || userLog.getId() == 0L) {
      autoIncreasingCount++;
      ScrapingUserLog newUserLog = ScrapingUserLog.builder()
          .id(autoIncreasingCount)
          .bojHandle(userLog.getBojHandle())
          .lastScrapingTime(userLog.getLastScrapingTime())
          .lastUserScrapingRequestTime(userLog.getLastUserScrapingRequestTime())
          .build();
      data.add(newUserLog);
      return newUserLog;
    } else {
      data.removeIf(item -> item.getId().equals(userLog.getId()));
      ScrapingUserLog newUserLog = ScrapingUserLog.builder()
          .id(userLog.getId())
          .bojHandle(userLog.getBojHandle())
          .lastScrapingTime(userLog.getLastScrapingTime())
          .lastUserScrapingRequestTime(userLog.getLastUserScrapingRequestTime())
          .build();
      data.add(newUserLog);
      return newUserLog;
    }
  }
}
