package com.randps.randomdefence.domain.scraping.service.mock;

import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import java.util.Optional;

public interface ScrapingUserLogRepository {

  Optional<ScrapingUserLog> findByBojHandle(String bojHandle);

  ScrapingUserLog save(ScrapingUserLog userLog);

}
