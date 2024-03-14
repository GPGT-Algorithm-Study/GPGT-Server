package com.randps.randomdefence.domain.complaint.dto;

import com.randps.randomdefence.domain.complaint.enums.ComplaintType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ComplaintUpdateRequest {

  private Long id;

  private String content;

  private ComplaintType complaintType;

}
