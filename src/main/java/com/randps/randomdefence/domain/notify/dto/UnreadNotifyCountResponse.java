package com.randps.randomdefence.domain.notify.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnreadNotifyCountResponse {

  private Long count;

  @Builder
  public UnreadNotifyCountResponse(Long count) {
    this.count = count;
  }

}
