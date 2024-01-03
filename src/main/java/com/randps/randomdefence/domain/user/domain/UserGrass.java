package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.domain.user.dto.UserGrassDto;
import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER_GRASS")
@Entity
public class UserGrass extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer problemId;

    private String date;

    private Boolean grassInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserRandomStreak userRandomStreak;

    @Builder
    public UserGrass(Long id, Integer problemId, String date, Boolean grassInfo, UserRandomStreak userRandomStreak) {
        this.id = id;
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

    public void infoCheckNo() {
        this.grassInfo = false;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }
}
