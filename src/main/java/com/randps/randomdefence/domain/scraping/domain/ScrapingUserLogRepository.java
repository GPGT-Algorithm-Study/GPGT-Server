package com.randps.randomdefence.domain.scraping.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapingUserLogRepository extends JpaRepository<ScrapingUserLog, Long> {

    Optional<ScrapingUserLog> findByBojHandle(String bojHandle);

}
