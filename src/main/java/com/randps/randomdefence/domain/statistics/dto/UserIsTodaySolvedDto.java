package com.randps.randomdefence.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIsTodaySolvedDto {

    // User
    private String notionId;

    private String emoji;

    private Boolean isTodaySolved;
}
