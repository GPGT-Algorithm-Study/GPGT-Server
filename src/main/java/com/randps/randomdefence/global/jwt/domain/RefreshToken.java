package com.randps.randomdefence.global.jwt.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import com.sun.istack.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "RD_REFRESH_TOKEN")
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String refreshToken;
    @NotNull
    private String bojHandle;

    @Builder
    public RefreshToken(Long id, String token, String bojHandle) {
        this.id = id;
        this.refreshToken = token;
        this.bojHandle = bojHandle;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}