package com.randps.randomdefence.domain.statistics.dto;

import com.randps.randomdefence.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YesterdayUnsolvedUserDto {
    private String bojHandle;

    private String notionId;

    private String profileImg;

    private String emoji;

    public YesterdayUnsolvedUserDto(User user) {
        this.bojHandle = user.getBojHandle();
        this.notionId = user.getNotionId();
        this.profileImg = user.getProfileImg();
        this.emoji = user.getEmoji();
    }
}
