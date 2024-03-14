package com.randps.randomdefence.domain.complaint.controller;

import com.randps.randomdefence.domain.complaint.dto.ComplaintDeleteRequest;
import com.randps.randomdefence.domain.complaint.dto.ComplaintDetailResponse;
import com.randps.randomdefence.domain.complaint.dto.ComplaintSaveRequest;
import com.randps.randomdefence.domain.complaint.dto.ComplaintUpdateRequest;
import com.randps.randomdefence.domain.complaint.service.ComplaintRequesterService;
import com.randps.randomdefence.domain.complaint.service.ComplaintSearchService;
import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import java.nio.file.AccessDeniedException;
import java.security.cert.CertificateExpiredException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/complaint/requester")
public class ComplaintRequesterController {

  private final ComplaintSearchService complaintSearchService;

  private final ComplaintRequesterService complaintRequesterService;

  private final JWTRefreshUtil jwtRefreshUtil;

  /*
   * 사용자가 자신의 모든 민원을 조회한다.
   */
  @GetMapping("/all")
  public List<ComplaintDetailResponse> searchAll(@RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    return complaintSearchService.findAllByRequesterSelf(jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자나 사용자가 민원을 등록한다.
   */
  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@RequestBody ComplaintSaveRequest request, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {
    complaintRequesterService.makeNewComplaint(request, jwtRefreshUtil.getBojHandle(refresh));

    HttpHeaders responseHeaders = new HttpHeaders();
    HttpStatus httpStatus = HttpStatus.OK;
    Map<String, String> map = new HashMap<>();
    map.put("type", httpStatus.getReasonPhrase());
    map.put("code", "200");
    map.put("message", "민원을 성공적으로 등록했습니다.");
    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }

  /*
   * 관리자나 사용자가 민원을 수정한다.
   */
  @PostMapping("/update")
  public ResponseEntity<Map<String, String>> update(@RequestBody ComplaintUpdateRequest request, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {
    complaintRequesterService.update(request, jwtRefreshUtil.getBojHandle(refresh));

    HttpHeaders responseHeaders = new HttpHeaders();
    HttpStatus httpStatus = HttpStatus.OK;
    Map<String, String> map = new HashMap<>();
    map.put("type", httpStatus.getReasonPhrase());
    map.put("code", "200");
    map.put("message", "민원을 성공적으로 수정했습니다.");
    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }

  /*
   * 관리자나 사용자가 민원을 삭제한다.
   */
  @DeleteMapping("/delete")
  public ResponseEntity<Map<String, String>> delete(@RequestBody ComplaintDeleteRequest request, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException, AccessDeniedException {
    complaintRequesterService.delete(request, jwtRefreshUtil.getBojHandle(refresh));

    HttpHeaders responseHeaders = new HttpHeaders();
    HttpStatus httpStatus = HttpStatus.OK;
    Map<String, String> map = new HashMap<>();
    map.put("type", httpStatus.getReasonPhrase());
    map.put("code", "200");
    map.put("message", "민원을 성공적으로 삭제했습니다.");
    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }

}
