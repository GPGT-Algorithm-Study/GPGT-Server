package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.domain.user.dto.UserSejongDto;
import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER_SEJONG")
@Entity
public class UserSejong extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle; // 아이디

    private String profileImg; // by Solved

    private Integer tier; // by Solved

    private Integer totalSolved; // by Solved

    @Builder
    public UserSejong(String bojHandle, String profileImg, Integer tier, Integer totalSolved) {
        this.bojHandle = bojHandle;
        this.profileImg = profileImg;
        this.tier = tier;
        this.totalSolved = totalSolved;
    }

    public void update(String bojHandle, String profileImg, Integer tier, Integer totalSolved) {
        this.bojHandle = bojHandle;
        this.profileImg = profileImg;
        this.tier = tier;
        this.totalSolved = totalSolved;
    }

    public UserSejongDto toDto() {
        return UserSejongDto.builder()
                .bojHandle(this.bojHandle)
                .profileImg(this.profileImg)
                .tier(this.tier)
                .totalSolved(this.totalSolved)
                .build();
    }
}
