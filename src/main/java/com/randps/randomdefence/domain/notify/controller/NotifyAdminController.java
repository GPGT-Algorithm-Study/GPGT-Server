package com.randps.randomdefence.domain.notify.controller;

import com.randps.randomdefence.domain.notify.service.NotifyAdminService;
import com.randps.randomdefence.domain.notify.service.NotifyService;
import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import java.security.cert.CertificateExpiredException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify/admin")
public class NotifyAdminController {

  private final NotifyAdminService notifyAdminService;

  private final NotifyService notifyService;

  private final JWTRefreshUtil jwtRefreshUtil;

  /*
   * 관리자가 특정 유저의 모든 알림을 삭제한다.
   */
  @DeleteMapping("/admin/receiver/all")
  public void deleteAllByReceiver(@RequestHeader("Refresh_Token") String refresh, @Param("receiver") String receiver)
      throws CertificateExpiredException {
    notifyAdminService.deleteAllByReceiver(receiver, jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 특정 알림을 삭제한다.
   */
  @DeleteMapping("/admin/id")
  public void deleteById(@RequestHeader("Refresh_Token") String refresh, @Param("id") Long id)
      throws CertificateExpiredException {
    notifyAdminService.deleteById(id, jwtRefreshUtil.getBojHandle(refresh));
  }

}
