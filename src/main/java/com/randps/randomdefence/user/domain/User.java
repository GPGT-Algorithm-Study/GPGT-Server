package com.randps.randomdefence.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private String notionId;

    private Boolean manager;

    private Integer warning;

    @Builder
    public User(String bojHandle, String notionId, Boolean manager, Integer warning) {
        this.bojHandle = bojHandle;
        this.notionId = notionId;
        this.manager = manager;
        this.warning = warning;
    }

}
