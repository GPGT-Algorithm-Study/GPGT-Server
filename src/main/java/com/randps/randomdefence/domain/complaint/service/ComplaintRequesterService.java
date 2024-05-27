package com.randps.randomdefence.domain.complaint.service;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.dto.ComplaintDeleteRequest;
import com.randps.randomdefence.domain.complaint.dto.ComplaintSaveRequest;
import com.randps.randomdefence.domain.complaint.dto.ComplaintUpdateRequest;
import com.randps.randomdefence.domain.complaint.service.port.ComplaintRepository;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.notify.service.NotifyService;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class ComplaintRequesterService {

  private final ComplaintRepository complaintRepository;

  private final UserRepository userRepository;

  private final NotifyService notifyService;

  /*
   * 민원인의 민원을 생성한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 요청자를 식별하고 민원을 생성할 수 있도록 한다.
   */
  @Transactional
  public Complaint makeNewComplaint(ComplaintSaveRequest request, String bojHandle)
      throws AccessDeniedException {
    User user = userRepository.findByBojHandle(request.getRequester())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    if (!request.getRequester().equals(bojHandle) && !user.getManager()) {
      throw new AccessDeniedException("이 민원을 생성할 권한이 없습니다.");
    }
    notifyService.systemPublishToAdmins("[" + user.getNotionId() + "]님이 작성한 새로운 민원이 등록되었습니다.",
        NotifyType.ADMIN, null);
    return complaintRepository.save(ComplaintSaveRequest.to(request));
  }

  /*
   * 민원인이 민원을 업데이트 한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 요청자를 식별하고 민원을 수정할 수 있도록 한다.
   */
  @Transactional
  public Complaint update(ComplaintUpdateRequest request, String bojHandle)
      throws AccessDeniedException {
    Complaint existingComplaint = complaintRepository.findById(request.getId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 민원입니다."));
    existingComplaint.updateContentAndType(request.getContent(), request.getComplaintType());
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    if (!existingComplaint.getRequester().equals(bojHandle) && !user.getManager()) {
      throw new AccessDeniedException("이 민원을 수정할 권한이 없습니다.");
    }
    return complaintRepository.save(existingComplaint);
  }

  /*
   * 민원을 삭제한다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 요청자를 식별하고 민원을 삭제할 수 있도록 한다.
   */
  @Transactional
  public void delete(ComplaintDeleteRequest request, String bojHandle)
      throws AccessDeniedException {
    Complaint complaint = complaintRepository.findById(request.getId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 민원입니다."));
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    User requester = userRepository.findByBojHandle(complaint.getRequester())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    if (!user.getBojHandle().equals(complaint.getRequester()) && !user.getManager()) {
      throw new AccessDeniedException("삭제 권한이 없습니다.");
    }

    notifyService.systemPublish(complaint.getRequester(), "😈 내 민원이 삭제되었습니다.",
        NotifyType.ADMIN, null);
    notifyService.systemPublishToAdmins(
        "[" + requester.getNotionId() + "]님이 작성한 기존의 민원을 " + user.getNotionId() + "님이 삭제했습니다.",
        NotifyType.ADMIN, null);

    complaintRepository.delete(complaint);
  }

}
