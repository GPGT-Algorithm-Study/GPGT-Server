package com.randps.randomdefence.domain.notify.service;

import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.notify.service.port.NotifyRepository;
import com.randps.randomdefence.domain.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class NotifyService {

  private final NotifyRepository notifyRepository;

  /*
   * 알림을 발행한다.
   */
  @Transactional
  public Notify publish(String receiver, String message, NotifyType type) {
    Notify notify = Notify.builder()
        .receiver(receiver)
        .message(message)
        .type(type)
        .build();
    return notifyRepository.save(notify);
  }

  /*
   * 특정 알림의 내용을 수정한다.
   */
  @Transactional
  public void update(Long id, String message) {
    Notify notify = notifyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));
    notify.updateMessage(message);
    notifyRepository.save(notify);
  }

  /*
   * 알림을 삭제한다.
   */
  @Transactional
  public void delete(Long id) {
    notifyRepository.deleteById(id);
  }

  /*
   * 특정 알람을 특정 유저들에게 발행한다.
   */
  @Transactional
  public void publishToUsers(List<User> receivers, String message, NotifyType type) {
    List<Notify> notifies = new ArrayList<>();

    for (User receiver : receivers) {
      Notify notify = Notify.builder()
          .receiver(receiver.getBojHandle())
          .message(message)
          .type(type)
          .build();
      notifies.add(notify);
    }
    notifyRepository.saveAll(notifies);
  }

}
