package com.randps.randomdefence.domain.complaint.controller;

import com.randps.randomdefence.domain.complaint.dto.ComplaintDetailResponse;
import com.randps.randomdefence.domain.complaint.dto.ComplaintProcessorUpdateRequest;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import com.randps.randomdefence.domain.complaint.service.ComplaintProcessorService;
import com.randps.randomdefence.domain.complaint.service.ComplaintSearchService;
import com.randps.randomdefence.global.jwt.component.JWTRefreshUtil;
import java.security.cert.CertificateExpiredException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/complaint/processor")
public class ComplaintProcessorController {

  private final ComplaintSearchService complaintSearchService;

  private final ComplaintProcessorService complaintProcessorService;

  private final JWTRefreshUtil jwtRefreshUtil;

  /*
   * 관리자가 모든 민원을 조회한다.
   */
  @GetMapping("/all")
  public List<ComplaintDetailResponse> searchAll(@RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    return complaintSearchService.findAll(jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 민원 번호를 정렬한 순서로 민원을 조회한다.
   */
  @GetMapping("/all/sort")
  public List<ComplaintDetailResponse> searchAllSort(@RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    return complaintSearchService.findAllByOrderByIdDesc(jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 요청자로 민원을 조회한다.
   */
  @GetMapping("/requester")
  public List<ComplaintDetailResponse> searchRequester(@Param("requester") String requester, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    return complaintSearchService.findAllByRequester(requester, jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 처리자로 민원을 조회한다.
   */
  @GetMapping("/processor")
  public List<ComplaintDetailResponse> searchProcessor(@Param("processor") String processor, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    return complaintSearchService.findAllByProcessor(processor, jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 민원 유형으로 민원을 조회한다.
   */
  @GetMapping("/complaint-type")
  public List<ComplaintDetailResponse> searchType(@Param("complaintType") ComplaintType complaintType, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    return complaintSearchService.findAllByComplaintType(complaintType, jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 처리 유형으로 민원을 조회한다.
   */
  @GetMapping("/process-type")
  public List<ComplaintDetailResponse> searchProcessType(@Param("processType") ProcessType processType, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    return complaintSearchService.findAllByProcessType(processType, jwtRefreshUtil.getBojHandle(refresh));
  }

  /*
   * 관리자가 민원의 상태를 바꾼다.
   */
  @PutMapping("/")
  public ResponseEntity<Map<String, String>> changeProcess(@RequestBody ComplaintProcessorUpdateRequest request, @RequestHeader("Refresh_Token") String refresh)
      throws CertificateExpiredException {
    complaintProcessorService.changeProcessType(request, jwtRefreshUtil.getBojHandle(refresh));

    HttpHeaders responseHeaders = new HttpHeaders();
    HttpStatus httpStatus = HttpStatus.OK;
    Map<String, String> map = new HashMap<>();
    map.put("type", httpStatus.getReasonPhrase());
    map.put("code", "200");
    map.put("message", "민원의 상태를 성공적으로 수정했습니다.");
    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }

}
