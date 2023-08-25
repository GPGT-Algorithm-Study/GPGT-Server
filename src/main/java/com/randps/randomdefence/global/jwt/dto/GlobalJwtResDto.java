package com.randps.randomdefence.global.jwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalJwtResDto {

    private String msg;
    private int statusCode;

    public GlobalJwtResDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}