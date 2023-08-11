package com.randps.randomdefence.scraping.domain;

import com.randps.randomdefence.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_SCRAPING")
@Entity
public class Scraping extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private String source;

    private String data;

    @Builder
    public Scraping(String bojHandle, String source, String data) {
        this.bojHandle = bojHandle;
        this.source = source;
        this.data = data;
    }

}
