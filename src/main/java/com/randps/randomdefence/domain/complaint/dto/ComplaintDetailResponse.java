package com.randps.randomdefence.domain.complaint.dto;

import com.randps.randomdefence.domain.complaint.domain.Complaint;
import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ComplaintDetailResponse {

  private Long id;

  private String requester;

  private String content;

  private ComplaintType complaintType;

  private ProcessType processType;

  private String processor;

  private String reply;

  public static ComplaintDetailResponse from(Complaint complaint) {
    return ComplaintDetailResponse.builder()
        .id(complaint.getId())
        .requester(complaint.getRequester())
        .content(complaint.getContent())
        .complaintType(complaint.getComplaintType())
        .processType(complaint.getProcessType())
        .processor(complaint.getProcessor())
        .reply(complaint.getReply())
        .build();
  }

  public static List<ComplaintDetailResponse> from(List<Complaint> complaints) {
    return complaints.stream().map(ComplaintDetailResponse::from).collect(Collectors.toList());
  }

}
