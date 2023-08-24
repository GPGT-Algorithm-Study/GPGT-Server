package com.randps.randomdefence.domain.user.dto;

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
    private String password;
    private String jwtToken;
}
