package com.randps.randomdefence.domain.scraping.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapingRepository extends JpaRepository<Scraping, Long> {
    // 스크래핑의 최화를 위해서 스크래핑 데이터를 저장해두고 사용.
    Optional<Scraping> findByBojHandleAndSource(String bojHandle, String source);
}
