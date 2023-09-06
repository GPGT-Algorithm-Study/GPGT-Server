package com.randps.randomdefence.domain.user.dto.authDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParseDto {
    private String claim;
    private Boolean manager;
}
