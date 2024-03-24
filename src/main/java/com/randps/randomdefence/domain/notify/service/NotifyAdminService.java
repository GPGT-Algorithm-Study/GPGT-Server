package com.randps.randomdefence.domain.notify.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.randps.randomdefence.domain.notify.service.port.NotifyRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.nio.file.AccessDeniedException;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class NotifyAdminService {

  private final NotifyRepository notifyRepository;

  private final UserRepository userRepository;

  /*
   * 특정 유저의 모든 알림을 삭제한다.
   */
  @Transactional
  public void deleteAllByReceiver(String receiver, String bojHandle) throws AccessDeniedException {
    User adminUser = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    if (!adminUser.getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    notifyRepository.deleteAllByReceiver(receiver);
  }

  /*
   * 특정 알림을 삭제한다.
   */
  @Transactional
  public void deleteById(Long id, String bojHandle) throws AccessDeniedException {
    User adminUser = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    notifyRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("알림이 존재하지 않습니다."));
    if (!adminUser.getManager()) {
      throw new AccessDeniedException("권한이 없습니다.");
    }

    notifyRepository.deleteById(id);
  }

}
