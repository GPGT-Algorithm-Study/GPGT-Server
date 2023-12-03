package com.randps.randomdefence.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSejongDto {

    private String bojHandle; // 아이디

    private String profileImg; // by Solved

    private Integer tier; // by Solved

    private Integer totalSolved; // by Solved

}
