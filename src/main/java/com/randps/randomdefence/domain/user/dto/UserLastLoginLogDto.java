package com.randps.randomdefence.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLastLoginLogDto {

    private String bojHandle;

    private String notionId;

    private String emoji;

    private String profileImg;

    private LocalDateTime lastLoginDate;
}
