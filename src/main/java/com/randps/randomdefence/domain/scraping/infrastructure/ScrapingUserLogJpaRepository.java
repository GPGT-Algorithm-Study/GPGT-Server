package com.randps.randomdefence.domain.scraping.infrastructure;

import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapingUserLogJpaRepository extends JpaRepository<ScrapingUserLog, Long> {

    Optional<ScrapingUserLog> findByBojHandle(String bojHandle);

}
