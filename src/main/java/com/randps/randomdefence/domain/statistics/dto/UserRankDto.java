package com.randps.randomdefence.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRankDto {

    private String bojHandle;

    private String profileImg;

    private Integer tier;

    private Integer totalSolved;

}
