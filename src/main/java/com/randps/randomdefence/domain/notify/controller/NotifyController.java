package com.randps.randomdefence.domain.notify.controller;

import static com.randps.randomdefence.global.component.util.ResponseUtil.toResponse;

import com.randps.randomdefence.domain.notify.dto.NotifyDeleteRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyPublishRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyPublishToUsersRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyReadRequest;
import com.randps.randomdefence.domain.notify.dto.NotifyUpdateRequest;
import com.randps.randomdefence.domain.notify.service.NotifyService;
import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import java.nio.file.AccessDeniedException;
import java.security.cert.CertificateExpiredException;
import java.util.Map;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
public class NotifyController {


  private final JWTRefreshUtil jwtRefreshUtil;

  private final NotifyService notifyService;

  /*
   * 관리자가 알림을 발행한다.
   */
  @Transactional
  @PostMapping("/")
  public ResponseEntity<Map<String, String>> publish(@RequestBody NotifyPublishRequest request,
      @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {

    notifyService.publish(request, jwtRefreshUtil.getBojHandle(refresh));

    return toResponse(HttpStatus.OK, "200", "알림을 성공적으로 발행했습니다.");
  }

  /*
   * 관리자가 특정 알림의 내용을 수정한다.
   */
  @Transactional
  @PutMapping("/")
  public ResponseEntity<Map<String, String>> update(@RequestBody NotifyUpdateRequest request,
      @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {

    notifyService.update(request, jwtRefreshUtil.getBojHandle(refresh));

    return toResponse(HttpStatus.OK, "200", "알림을 성공적으로 수정했습니다.");
  }

  /*
   * 관리자가 특정 알림을 삭제한다.
   */
  @Transactional
  @DeleteMapping("/")
  public ResponseEntity<Map<String, String>> delete(@RequestBody NotifyDeleteRequest request,
      @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {

    notifyService.delete(request, jwtRefreshUtil.getBojHandle(refresh));

    return toResponse(HttpStatus.OK, "200", "알림을 성공적으로 삭제했습니다.");
  }

  /*
   * 관리자가 특정 알람을 특정 유저들에게 발행한다.
   */
  @Transactional
  @PostMapping("/users")
  public ResponseEntity<Map<String, String>> publishToUsers(
      @RequestBody NotifyPublishToUsersRequest request,
      @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {

    notifyService.publishToUsers(request, jwtRefreshUtil.getBojHandle(refresh));

    return toResponse(HttpStatus.OK, "200", "알림을 성공적으로 발행했습니다.");
  }

  /*
   * 특정 알림을 읽었다고 처리한다.
   */
  @Transactional
  @PutMapping("/read")
  public ResponseEntity<Map<String, String>> read(@RequestBody NotifyReadRequest request,
      @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {

    notifyService.read(request, jwtRefreshUtil.getBojHandle(refresh));

    return toResponse(HttpStatus.OK, "200", "알림을 성공적으로 읽었습니다.");
  }
}
