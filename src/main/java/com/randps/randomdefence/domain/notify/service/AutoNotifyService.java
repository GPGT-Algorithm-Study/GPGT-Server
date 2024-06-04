package com.randps.randomdefence.domain.notify.service;

import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.global.event.notify.entity.NotifyToUserBySystemEvent;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutoNotifyService {

  private final UserRepository userRepository;

  private final ApplicationContext applicationContext;

  @Transactional
  public void weekendWarningNotify() {
    List<User> users = userRepository.findAll();

    for (User user : users) {
      if (user.getWarning() == 3) {
        applicationContext.publishEvent(
            new NotifyToUserBySystemEvent(this, user.getBojHandle(),
                "🚨 현재 경고 3회 입니다. 경고를 차감해주세요", NotifyType.SYSTEM, null));
      }
    }
  }

}
