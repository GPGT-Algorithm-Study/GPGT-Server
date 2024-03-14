package com.randps.randomdefence.domain.user.dto;

import com.randps.randomdefence.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMentionDto {

    private String notionId;

    public UserMentionDto(User user) {
        this.notionId = user.getNotionId();
    }
}
