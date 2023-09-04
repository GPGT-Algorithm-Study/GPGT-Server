package com.randps.randomdefence.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWarningBarDto {

    // User
    private String notionId;

    private String emoji;

    private Integer warning;

}
