package com.randps.randomdefence.domain.user.infrastructure;

import com.randps.randomdefence.domain.user.domain.UserSetting;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingJpaRepository extends JpaRepository<UserSetting, Long> {

    Optional<UserSetting> findByBojHandle(String bojHandle);

}
