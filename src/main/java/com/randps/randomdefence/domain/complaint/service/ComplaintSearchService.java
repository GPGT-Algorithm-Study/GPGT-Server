package com.randps.randomdefence.domain.complaint.service;

import com.randps.randomdefence.domain.complaint.dto.ComplaintDetailResponse;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import com.randps.randomdefence.domain.complaint.service.port.ComplaintRepository;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class ComplaintSearchService {

  private final ComplaintRepository complaintRepository;

  private final UserRepository userRepository;

  /*
   * 민원인이 자신의 모든 민원을 조회한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 조회할 수 있도록 한다.
   */
  public List<ComplaintDetailResponse> findAllByRequesterSelf(String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    return ComplaintDetailResponse.from(complaintRepository.findAllByRequester(bojHandle));
  }
  /*
   * 모든 민원을 조회한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 조회할 수 있도록 한다.
   */
  public List<ComplaintDetailResponse> findAll(String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    if (!user.getManager()) {
      throw new IllegalArgumentException("이 민원을 조회할 권한이 없습니다.");
    }

    return ComplaintDetailResponse.from(complaintRepository.findAll());
  }

  /*
   * 민원 번호를 정렬한 순서로로 민원을 조회한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 조회할 수 있도록 한다.
   */
  public List<ComplaintDetailResponse> findAllByOrderByIdDesc(String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    if (!user.getManager()) {
      throw new IllegalArgumentException("이 민원을 조회할 권한이 없습니다.");
    }

    return ComplaintDetailResponse.from(complaintRepository.findAllByOrderByIdDesc());
  }

  /*
   * 요청자로 민원을 조회한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 조회할 수 있도록 한다.
   */
  public List<ComplaintDetailResponse> findAllByRequester(String requester, String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    if (!user.getManager()) {
      throw new IllegalArgumentException("이 민원을 조회할 권한이 없습니다.");
    }

    return ComplaintDetailResponse.from(complaintRepository.findAllByRequester(requester));
  }

  /*
   * 처리자로 민원을 조회한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 조회할 수 있도록 한다.
   */
  public List<ComplaintDetailResponse> findAllByProcessor(String processor, String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    if (!user.getManager()) {
      throw new IllegalArgumentException("이 민원을 조회할 권한이 없습니다.");
    }

    return ComplaintDetailResponse.from(complaintRepository.findAllByProcessor(processor));
  }

  /*
   * 민원 유형으로 민원을 조회한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 조회할 수 있도록 한다.
   */
  public List<ComplaintDetailResponse> findAllByComplaintType(ComplaintType complaintType, String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    if (!user.getManager()) {
      throw new IllegalArgumentException("이 민원을 조회할 권한이 없습니다.");
    }

    return ComplaintDetailResponse.from(complaintRepository.findAllByComplaintType(complaintType));
  }

  /*
   * 처리 유형으로 민원을 조회한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 조회할 수 있도록 한다.
   */
  public List<ComplaintDetailResponse> findAllByProcessType(ProcessType processType, String bojHandle) {
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    if (!user.getManager()) {
      throw new IllegalArgumentException("이 민원을 조회할 권한이 없습니다.");
    }

    return ComplaintDetailResponse.from(complaintRepository.findAllByProcessType(processType));
  }

}
