package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserSetting;
import com.randps.randomdefence.domain.user.service.port.UserSettingRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSettingRepositoryAdapter implements UserSettingRepository {

  private final UserSettingJpaRepository userSettingJpaRepository;

  @Override
  public Optional<UserSetting> findByBojHandle(String bojHandle) {
    return userSettingJpaRepository.findByBojHandle(bojHandle);
  }

  @Override
  public UserSetting save(UserSetting userSetting) {
    return userSettingJpaRepository.save(userSetting);
  }

  @Override
  public void delete(UserSetting userSetting) {
    userSettingJpaRepository.delete(userSetting);
  }

}
