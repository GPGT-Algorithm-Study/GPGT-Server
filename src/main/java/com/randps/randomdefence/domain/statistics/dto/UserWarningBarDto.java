package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWarningBarDto {

    // User
    private String notionId;

    private String emoji;

    private Integer warning;

    public UserWarningBarDto(User user) {
        this.notionId = user.getNotionId();
        this.emoji = user.getEmoji();
        this.warning = user.getWarning();
    }

}
