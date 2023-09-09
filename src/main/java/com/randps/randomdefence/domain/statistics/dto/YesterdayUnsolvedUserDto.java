package com.randps.randomdefence.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YesterdayUnsolvedUserDto {
    private String bojHandle;

    private String notionId;

    private String profileImg;

    private String emoji;
}
