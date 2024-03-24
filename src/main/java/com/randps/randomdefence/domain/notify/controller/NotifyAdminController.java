package com.randps.randomdefence.domain.notify.controller;

import static com.randps.randomdefence.global.component.util.ResponseUtil.toResponse;

import com.randps.randomdefence.domain.notify.dto.NotifyDeleteRequest;
import com.randps.randomdefence.domain.notify.service.NotifyAdminService;
import com.randps.randomdefence.domain.notify.service.NotifyService;
import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import java.nio.file.AccessDeniedException;
import java.security.cert.CertificateExpiredException;
import java.util.Map;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  @DeleteMapping("/receiver/all")
  public ResponseEntity<Map<String, String>> deleteAllByReceiver(
      @RequestHeader("Refresh_Token") String refresh, @Param("receiver") String receiver)
      throws CertificateExpiredException, AccessDeniedException {
    notifyAdminService.deleteAllByReceiver(receiver, jwtRefreshUtil.getBojHandle(refresh));

    return toResponse(HttpStatus.OK, "200", "해당 유저의 모든 알림을 성공적으로 삭제했습니다.");
  }

  /*
   * 관리자가 특정 알림을 삭제한다.
   */
  @DeleteMapping("/id")
  public ResponseEntity<Map<String, String>> deleteById(
      @RequestHeader("Refresh_Token") String refresh,
      @RequestBody NotifyDeleteRequest request)
      throws CertificateExpiredException, AccessDeniedException {
    notifyAdminService.deleteById(request.getId(), jwtRefreshUtil.getBojHandle(refresh));

    return toResponse(HttpStatus.OK, "200", "알림을 성공적으로 삭제했습니다.");
  }

}
