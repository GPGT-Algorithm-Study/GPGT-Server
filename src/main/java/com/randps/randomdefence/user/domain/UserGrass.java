package com.randps.randomdefence.user.domain;

import com.randps.randomdefence.user.dto.UserGrassDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER_GRASS")
@Entity
public class UserGrass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer problemId;

    private String date;

    private Boolean grassInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserRandomStreak userRandomStreak;

    @Builder
    public UserGrass(Integer problemId, String date, Boolean grassInfo, UserRandomStreak userRandomStreak) {
        this.problemId = problemId;
        this.date = date;
        this.grassInfo = grassInfo;
        this.userRandomStreak = userRandomStreak;
    }

    public UserGrassDto toDto() {
        return UserGrassDto.builder()
                .problemId(this.getProblemId())
                .date(this.getDate())
                .grassInfo(this.getGrassInfo())
                .build();
    }

    public void infoCheckOk() {
        this.grassInfo = true;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }
}
