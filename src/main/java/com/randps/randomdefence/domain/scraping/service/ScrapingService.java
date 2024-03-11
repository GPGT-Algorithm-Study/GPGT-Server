package com.randps.randomdefence.domain.scraping.service;

import com.randps.randomdefence.domain.scraping.domain.Scraping;
import com.randps.randomdefence.domain.scraping.infrastructure.ScrapingRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScrapingService {
    private final ScrapingRepository scrapingRepository;

    /*
     * solvedac의 스크래핑 소스를 통으로 저장.. 한다
     */
    @Transactional
    public void saveSolvedacUserInfo(String bojHandle, String solvedacUserInfo) {
        Scraping scraping = Scraping.builder()
                .bojHandle(bojHandle)
                .source("solvedac")
                .data(solvedacUserInfo)
                .build();

        scrapingRepository.save(scraping);
    }

    /*
     * 백준의 스크래핑 소스를 통으로.. 저장.. 한다.
     */
    @Transactional
    public void saveBojUserSolvedInfo(String bojHandle, String bojUserSolvedInfo) {
        Scraping scraping = Scraping.builder()
                .bojHandle(bojHandle)
                .source("baekjoon")
                .data(bojUserSolvedInfo)
                .build();

        scrapingRepository.save(scraping);
    }

    /*
     * 저장한 솔브닥의 스크래핑 데이터를 불러온다.
     */
    @Transactional
    public String findSolvedacUserInfo(String bojHandle) {
        Scraping solvedacUserInfo = scrapingRepository.findByBojHandleAndSource(bojHandle, "solvedac")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보입니다."));

        return solvedacUserInfo.getData();
    }

    /*
     * 저장한 백준의 스크래핑 데이터를 불러온다
     */
    @Transactional
    public String findBojUserInfo(String bojHandle) {
        Scraping bojUserInfo = scrapingRepository.findByBojHandleAndSource(bojHandle, "baekjoon")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보입니다."));

        return bojUserInfo.getData();
    }
}
