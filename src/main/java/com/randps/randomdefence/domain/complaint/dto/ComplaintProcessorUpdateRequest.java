package com.randps.randomdefence.domain.complaint.dto;

import com.randps.randomdefence.domain.complaint.enums.ProcessType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ComplaintProcessorUpdateRequest {

  private Long id;

  private ProcessType processType;

  private String processor;

  private String reply;

}
