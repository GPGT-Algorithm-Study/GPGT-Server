package com.randps.randomdefence.domain.team.domain;

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
    public Team(Long id, Integer teamNumber, String teamName, Integer teamPoint) {
        this.id = id;
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
