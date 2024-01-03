package com.randps.randomdefence.domain.boolshit.dto;

import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import com.randps.randomdefence.domain.user.dto.UserInfoResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoolshitResponse {

    private Long id;

    private String message;

    private UserInfoResponse user;

    private LocalDateTime writtenDate;

    public BoolshitResponse from(Boolshit boolshit, UserInfoResponse userInfoResponse) {
        return BoolshitResponse.builder()
                .id(boolshit.getId())
                .message(boolshit.getMessage())
                .user(userInfoResponse)
                .writtenDate(boolshit.getCreatedDate())
                .build();
    }
}
