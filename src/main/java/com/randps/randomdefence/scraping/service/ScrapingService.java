package com.randps.randomdefence.scraping.service;

import com.randps.randomdefence.scraping.domain.Scraping;
import com.randps.randomdefence.scraping.domain.ScrapingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ScrapingService {
    private final ScrapingRepository scrapingRepository;

    @Transactional
    public void saveSolvedacUserInfo(String bojHandle, String solvedacUserInfo) {
        Scraping scraping = Scraping.builder()
                .bojHandle(bojHandle)
                .source("solvedac")
                .data(solvedacUserInfo)
                .build();

        scrapingRepository.save(scraping);
    }

    @Transactional
    public void saveBojUserSolvedInfo(String bojHandle, String bojUserSolvedInfo) {
        Scraping scraping = Scraping.builder()
                .bojHandle(bojHandle)
                .source("baekjoon")
                .data(bojUserSolvedInfo)
                .build();

        scrapingRepository.save(scraping);
    }

    @Transactional
    public String findSolvedacUserInfo(String bojHandle) {
        Scraping solvedacUserInfo = scrapingRepository.findByBojHandleAndSource(bojHandle, "solvedac")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보입니다."));

        return solvedacUserInfo.getData();
    }

    @Transactional
    public String findBojUserInfo(String bojHandle) {
        Scraping bojUserInfo = scrapingRepository.findByBojHandleAndSource(bojHandle, "baekjoon")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보입니다."));

        return bojUserInfo.getData();
    }
}
