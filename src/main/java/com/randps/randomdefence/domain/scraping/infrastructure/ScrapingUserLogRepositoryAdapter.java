package com.randps.randomdefence.domain.scraping.infrastructure;

import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import com.randps.randomdefence.domain.scraping.service.mock.ScrapingUserLogRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScrapingUserLogRepositoryAdapter implements ScrapingUserLogRepository {

  private final ScrapingUserLogJpaRepository scrapingUserLogJpaRepository;

  @Override
  public Optional<ScrapingUserLog> findByBojHandle(String bojHandle) {
    return scrapingUserLogJpaRepository.findByBojHandle(bojHandle);
  }

  @Override
  public ScrapingUserLog save(ScrapingUserLog userLog) {
    return scrapingUserLogJpaRepository.save(userLog);
  }

}
