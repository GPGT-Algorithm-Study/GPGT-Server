package com.randps.randomdefence.domain.user.dto.authDto;

import com.randps.randomdefence.global.jwt.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginSuccessResponse {
    private String bojHandle;
    private Boolean manager;
    private TokenDto jwt;
}
