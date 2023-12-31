package com.randps.randomdefence.domain.scraping.service;

import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLog;
import com.randps.randomdefence.domain.scraping.domain.ScrapingUserLogRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScrapingUserLogService {

    private final ScrapingUserLogRepository scrapingUserLogRepository;

    @Transactional
    public Boolean isPossible(String bojHandle) {
        ScrapingUserLog userLog = scrapingUserLogRepository.findByBojHandle(bojHandle).orElseGet(() -> ScrapingUserLog.builder().bojHandle(bojHandle).build());

        if (!userLog.isScrapingPossible()) {
            return false;
        }
        userLog.saveLastScrapingTime();
        scrapingUserLogRepository.save(userLog);
        return true;
    }
}
