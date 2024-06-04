package com.randps.randomdefence.domain.complaint.service;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.dto.ComplaintProcessorUpdateRequest;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import com.randps.randomdefence.domain.complaint.service.port.ComplaintRepository;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import com.randps.randomdefence.global.event.notify.entity.NotifyToUserBySystemEvent;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class ComplaintProcessorService {

  private final ComplaintRepository complaintRepository;

  private final UserRepository userRepository;

  private final ApplicationContext applicationContext;

  /*
   * 민원의 상태를 바꾼다.
   * 여기에 파라미터로 들어오는 bojHandle은 JWT에서 가져온 본인의 식별자로, 이를 통해 권한을 식별하고 민원을 수정할 수 있도록 한다.
   */
  @Transactional
  public void changeProcessType(ComplaintProcessorUpdateRequest request, String bojHandle) {
    Complaint complaint = complaintRepository.findById(request.getId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 민원입니다."));
    User user = userRepository.findByBojHandle(bojHandle)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    if (!user.getManager()) {
      throw new IllegalArgumentException("이 민원을 수정할 권한이 없습니다.");
    }
    if (request.getProcessType() == null) {
      throw new IllegalArgumentException("처리 유형을 입력해주세요.");
    }
    if (request.getProcessType().equals(ProcessType.WAITING)) {
      complaint.setProcessWAITING();
      applicationContext.publishEvent(
          new NotifyToUserBySystemEvent(this, complaint.getRequester(), "😴 내 민원이 [대기] 상태로 변경되었습니다.",
              NotifyType.ADMIN, null));
    }
    if (request.getProcessType().equals(ProcessType.PROCESSING)) {
      complaint.setProcessPROCESSING();
      applicationContext.publishEvent(new NotifyToUserBySystemEvent(this, complaint.getRequester(),
          "😎 내 민원이 [처리 중] 상태로 변경되었습니다.", NotifyType.ADMIN, null));
    }
    if (request.getProcessType().equals(ProcessType.DONE)) {
      complaint.setProcessDONE();

      applicationContext.publishEvent(new NotifyToUserBySystemEvent(this, complaint.getRequester(),
          "🥳 내 민원이 [처리 완료] 상태로 변경되었습니다.", NotifyType.ADMIN, null));
    }
    if (request.getReply() != null) {
      complaint.updateProcessorAndReply(request.getProcessor(), request.getReply());
    } else {
      complaint.updateProcessorAndReply(request.getProcessor(), complaint.getReply());
    }

    complaintRepository.save(complaint);
  }


}
