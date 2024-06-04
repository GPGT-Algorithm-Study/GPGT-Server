package com.randps.randomdefence.domain.user.service.port;

import com.randps.randomdefence.domain.user.domain.UserSetting;
import java.util.Optional;

public interface UserSettingRepository {

  Optional<UserSetting> findByBojHandle(String bojHandle);

  UserSetting save(UserSetting userSetting);

  void delete(UserSetting userSetting);

}
