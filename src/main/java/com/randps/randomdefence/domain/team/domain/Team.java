package com.randps.randomdefence.domain.team.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_TEAM")
@Entity
public class Team extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer teamNumber;

    private String teamName;

    private Integer teamPoint;

    @Builder
    public Team(Integer teamNumber, String teamName, Integer teamPoint) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.teamPoint = teamPoint;
    }

    public void increasePoint(Integer value) {
        this.teamPoint += value;
    }

    public void decreasePoint(Integer value) {
        this.teamPoint -= value;
    }

    public void resetTeamPoint() {
        this.teamPoint = 0;
    }
}
