package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.domain.UserSetting;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.domain.user.service.port.UserSettingRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingSearchService {

  private final UserRepository userRepository;

  private final UserSettingRepository userSettingRepository;

  public UserSetting findByBojHandleSafe(String bojHandle) {
    userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

    return userSettingRepository.findByBojHandle(bojHandle)
        .orElseGet(() -> {
          UserSetting userSetting = UserSetting.builder()
              .bojHandle(bojHandle)
              .scrapingOn(true)
              .warningOn(true)
              .build();
          return userSettingRepository.save(userSetting);
        });
  }

  public List<UserSetting> findAllSafe() {
    List<User> users = userRepository.findAll();
    List<UserSetting> userSettings = new ArrayList<>();

    for (User user : users) {
      userSettings.add(userSettingRepository.findByBojHandle(user.getBojHandle())
          .orElseGet(() -> {
            UserSetting userSetting = UserSetting.builder()
                .bojHandle(user.getBojHandle())
                .scrapingOn(true)
                .warningOn(true)
                .build();
            return userSettingRepository.save(userSetting);
          }));
    }
    return userSettings;
  }

}
