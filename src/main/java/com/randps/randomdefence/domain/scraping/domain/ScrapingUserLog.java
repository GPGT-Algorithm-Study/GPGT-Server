package com.randps.randomdefence.domain.scraping.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "RD_SCRAPING_USER_LOG")
@Entity
public class ScrapingUserLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private LocalDateTime lastScrapingTime;

    @Builder
    public ScrapingUserLog(String bojHandle) {
        this.bojHandle = bojHandle;
        this.lastScrapingTime = LocalDateTime.now().minusMinutes(21);
    }

    public Boolean isScrapingPossible() {
        return this.lastScrapingTime.isBefore(LocalDateTime.now().minusMinutes(20));
    }

    public void saveLastScrapingTime() {
        this.lastScrapingTime = LocalDateTime.now();
    }
}
