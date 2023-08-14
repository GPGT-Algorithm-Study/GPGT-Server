package com.randps.randomdefence.user.domain;

import com.randps.randomdefence.auditing.BaseTimeEntity;
import com.randps.randomdefence.component.parser.dto.UserScrapingInfoDto;
import com.randps.randomdefence.user.dto.UserInfoResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private String notionId;

    private Boolean manager;

    private Integer warning;

    private String profileImg; // by Solved

    private String emoji;

    private Integer tier; // by Solved

    private Integer totalSolved; // by Solved

    private Integer currentStreak; // by Solved

    private Integer currentRandomStreak;

    private Integer team;

    private Integer point;

    private Boolean isTodaySolved; // by Solved

    private Boolean isTodayRandomSolved;

    @Builder
    public User(String bojHandle, String notionId, Boolean manager, Integer warning, String profileImg, String emoji, Integer tier, Integer totalSolved, Integer currentStreak, Integer currentRandomStreak, Integer team, Integer point, Boolean isTodaySolved, Boolean isTodayRandomSolved) {
        this.bojHandle = bojHandle;
        this.notionId = notionId;
        this.manager = manager;
        this.warning = warning;
        this.profileImg = profileImg;
        this.emoji = emoji;
        this.tier = tier;
        this.totalSolved = totalSolved;
        this.currentStreak = currentStreak;
        this.currentRandomStreak = currentRandomStreak;
        this.team = team;
        this.point = point;
        this.isTodaySolved = isTodaySolved;
        this.isTodayRandomSolved = isTodayRandomSolved;
    }

    public void increaseWarning() {
        if (this.warning < 4)
            this.warning++;
    }

    public void decreaseWarning() {
        if (this.warning > 1)
            this.warning--;
    }

    public Boolean increasePoint(Integer value) {
        this.point += value;
        return true;
    }

    public Boolean decreasePoint(Integer value) {
        if (this.point < value) return false;
        this.point -= value;
        return true;
    }

    public void checkTodayRandomSolvedOk() {
        this.isTodayRandomSolved = true;
    }

    public void checkTodayRandomSolvedNo() {
        this.isTodayRandomSolved = false;
    }

    public void increaseCurrentRandomStreak() {
        this.currentRandomStreak++;
    }

    public void resetCurrentRandomStreak() {
        this.currentRandomStreak = 0;
    }

    public UserInfoResponse toUserInfoResponse() {
        return UserInfoResponse.builder()
                .bojHandle(this.getBojHandle())
                .notionId(this.getNotionId())
                .manager(this.getManager())
                .warning(this.getWarning())
                .profileImg(this.getProfileImg())
                .emoji(this.getEmoji())
                .tier(this.getTier())
                .totalSolved(this.getTotalSolved())
                .currentStreak(this.getCurrentStreak())
                .currentRandomStreak(this.getCurrentRandomStreak())
                .team(this.getTeam())
                .point(this.getPoint())
                .isTodaySolved(this.getIsTodaySolved())
                .isTodayRandomSolved(this.getIsTodayRandomSolved())
                .build();
    }

    public void setScrapingUserInfo(UserScrapingInfoDto userInfo) {
        this.profileImg = userInfo.getProfileImg();
        this.tier = userInfo.getTier();
        this.totalSolved = userInfo.getTotalSolved();
        this.currentStreak = userInfo.getCurrentStreak();
        this.isTodaySolved = userInfo.getIsTodaySolved();
    }
}
