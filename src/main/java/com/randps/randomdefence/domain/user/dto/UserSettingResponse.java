package com.randps.randomdefence.domain.user.dto;

import com.randps.randomdefence.domain.user.domain.UserSetting;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSettingResponse {

  private String bojHandle;

  private boolean scrapingOn;

  private boolean warningOn;

  static public UserSettingResponse from(UserSetting userSetting) {
    return UserSettingResponse.builder()
        .bojHandle(userSetting.getBojHandle())
        .scrapingOn(userSetting.getScrapingOn())
        .warningOn(userSetting.getWarningOn())
        .build();
  }

}
