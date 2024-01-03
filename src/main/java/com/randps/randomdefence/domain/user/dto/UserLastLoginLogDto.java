package com.randps.randomdefence.domain.user.dto;

import com.randps.randomdefence.domain.user.domain.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLastLoginLogDto {

    private String bojHandle;

    private String notionId;

    private String emoji;

    private String profileImg;

    private LocalDateTime lastLoginDate;

    public UserLastLoginLogDto(User user) {
        this.bojHandle = user.getBojHandle();
        this.notionId = user.getNotionId();
        this.emoji = user.getEmoji();
        this.profileImg = user.getProfileImg();
        this.lastLoginDate = user.getCreatedDate();
    }
}
