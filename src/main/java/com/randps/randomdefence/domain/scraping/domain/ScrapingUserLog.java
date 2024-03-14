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

    private LocalDateTime lastUserScrapingRequestTime;

    @Builder
    public ScrapingUserLog(Long id, String bojHandle, LocalDateTime lastScrapingTime,
        LocalDateTime lastUserScrapingRequestTime) {
        this.id = id;
        this.bojHandle = bojHandle;
        this.lastScrapingTime = lastScrapingTime;
        this.lastUserScrapingRequestTime = lastUserScrapingRequestTime;
    }

    public Boolean isScrapingPossible() {
        if (this.lastUserScrapingRequestTime == null) {
            return true;
        }
        return this.lastUserScrapingRequestTime.isBefore(LocalDateTime.now().minusMinutes(20));
    }

    public void saveLastUserScrapingRequestTime(LocalDateTime lastUserScrapingRequestTime) {
        this.lastUserScrapingRequestTime = lastUserScrapingRequestTime;
    }

    public void saveLastScrapingTime(LocalDateTime lastScrapingTime) {
        this.lastScrapingTime = lastScrapingTime;
    }
}
