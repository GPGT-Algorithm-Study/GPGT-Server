package com.randps.randomdefence.domain.notify.service;

import com.randps.randomdefence.domain.notify.domain.Notify;
import com.randps.randomdefence.domain.notify.dto.NotifyDeleteRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyPublishRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyPublishToUsersRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyReadRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyUpdateRequest;
import com.randps.randomdefence.domain.notify.service.port.NotifyRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.nio.file.AccessDeniedException;
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

  private final UserRepository userRepository;

  /*
   * 알림을 발행한다.
   */
  @Transactional
  public Notify publish(NotifyPublishRequest request, String sender) throws AccessDeniedException {
    if (!userRepository.findByBojHandle(sender)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")).getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }
    if (userRepository.findByBojHandle(request.getReceiver()).isEmpty()) {
      throw new IllegalArgumentException("수신자가 존재하지 않습니다.");
    }

    Notify notify = Notify.builder()
        .receiver(request.getReceiver())
        .message(request.getMessage())
        .type(request.getType())
        .build();
    return notifyRepository.save(notify);
  }

  /*
   * 특정 알림의 내용을 수정한다.
   */
  @Transactional
  public void update(NotifyUpdateRequest request, String bojHandle) throws AccessDeniedException {
    User requestor = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    Notify notify = notifyRepository.findById(request.getId())
        .orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));
    if (!requestor.getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }
    notify.updateMessage(request.getMessage());
    notify.updateType(request.getType());
    notifyRepository.save(notify);
  }

  /*
   * 알림을 삭제한다.
   */
  @Transactional
  public void delete(NotifyDeleteRequest request, String bojHandle) throws AccessDeniedException {
    if (!userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")).getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    notifyRepository.deleteById(request.getId());
  }

  /*
   * 특정 알람을 특정 유저들에게 발행한다.
   */
  @Transactional
  public void publishToUsers(NotifyPublishToUsersRequest request, String bojHandle)
      throws AccessDeniedException {
    if (!userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")).getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    List<Notify> notifies = new ArrayList<>();

    for (String receiver : request.getReceivers()) {
      if (userRepository.findByBojHandle(receiver).isEmpty()) {
        continue;
      }
      Notify notify = Notify.builder()
          .receiver(receiver)
          .message(request.getMessage())
          .type(request.getType())
          .build();
      notifies.add(notify);
    }
    notifyRepository.saveAll(notifies);
  }

  /*
   * 특정 알람을 읽었다고 처리한다.
   */
  @Transactional
  public void read(NotifyReadRequest request, String bojHandle) throws AccessDeniedException {
    userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    Notify notify = notifyRepository.findById(request.getId())
        .orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));

    if (!notify.getReceiver().equals(bojHandle)) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    notify.read();
    notifyRepository.save(notify);
  }
}
