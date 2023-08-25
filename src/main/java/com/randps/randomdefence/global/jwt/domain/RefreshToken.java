package com.randps.randomdefence.global.jwt.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public RefreshToken(String token, String bojHandle) {
        this.refreshToken = token;
        this.bojHandle = bojHandle;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}