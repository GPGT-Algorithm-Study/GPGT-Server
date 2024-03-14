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
public class ComplaintSimpleResponse {

  private Long id;

  private String requester;

  private String content;

  private ComplaintType complaintType;

  private ProcessType processType;

  private String processor;

  public static ComplaintSimpleResponse from(Complaint complaint) {
    return ComplaintSimpleResponse.builder()
        .id(complaint.getId())
        .requester(complaint.getRequester())
        .content(complaint.getContent())
        .complaintType(complaint.getComplaintType())
        .processType(complaint.getProcessType())
        .processor(complaint.getProcessor())
        .build();
  }

  public static List<ComplaintSimpleResponse> from(List<Complaint> complaints) {
    return complaints.stream().map(ComplaintSimpleResponse::from).collect(Collectors.toList());
  }

}
