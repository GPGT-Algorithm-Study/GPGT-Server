package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIsTodaySolvedDto {

    // User
    private String notionId;

    private String emoji;

    private Boolean isTodaySolved;

    public UserIsTodaySolvedDto(User user) {
        this.notionId = user.getNotionId();
        this.emoji = user.getEmoji();
        this.isTodaySolved = user.getIsTodaySolved();
    }
}
