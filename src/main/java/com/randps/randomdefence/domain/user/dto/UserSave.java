package com.randps.randomdefence.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSave {

    private String bojHandle;

    private String password;

    private String notionId;

    private Long manager;

    private String emoji;

}
