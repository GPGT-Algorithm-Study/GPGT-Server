package com.randps.randomdefence.domain.complaint.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComplaintDeleteRequest {

  private Long id;

  @Builder
  public ComplaintDeleteRequest(Long id) {
    this.id = id;
  }

}
