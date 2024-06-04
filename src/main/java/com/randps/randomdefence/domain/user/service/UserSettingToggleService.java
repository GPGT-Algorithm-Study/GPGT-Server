package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.UserSetting;
import com.randps.randomdefence.domain.user.service.port.UserSettingRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingToggleService {

  private final UserSettingRepository userSettingRepository;

  private final UserSettingSearchService userSettingSearchService;

  @Transactional
  public void toggleScrapingSetting(String bojHandle) {
    UserSetting userSetting = userSettingSearchService.findByBojHandleSafe(bojHandle);
    userSetting.toggleScraping();
    userSettingRepository.save(userSetting);
  }

  @Transactional
  public void toggleWarningSetting(String bojHandle) {
    UserSetting userSetting = userSettingSearchService.findByBojHandleSafe(bojHandle);
    userSetting.toggleWarning();
    userSettingRepository.save(userSetting);
  }

}
