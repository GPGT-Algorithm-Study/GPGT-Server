package com.randps.randomdefence.domain.boolshit.dto;

import com.randps.randomdefence.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoolshitResponse {

    private Long id;

    private String message;

    private User user;

    private LocalDateTime writtenDate;
}
