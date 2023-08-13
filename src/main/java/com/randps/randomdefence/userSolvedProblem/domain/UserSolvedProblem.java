package com.randps.randomdefence.userSolvedProblem.domain;

import com.randps.randomdefence.auditing.BaseTimeEntity;
import com.randps.randomdefence.userSolvedProblem.dto.SolvedProblemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER_SOLVED_PROBLEM")
@Entity
public class UserSolvedProblem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer problemId;

    private String bojHandle;

    private String title;

    private String dateTime;

    @Builder
    public UserSolvedProblem(String bojHandle, Integer problemId, String title, String dateTime) {
        this.bojHandle = bojHandle;
        this.problemId = problemId;
        this.title = title;
        this.dateTime = dateTime;
    }

    public SolvedProblemDto toDto() {
        return SolvedProblemDto.builder()
                .problemId(this.getProblemId())
                .title(this.getTitle())
                .dateTime(this.getDateTime())
                .build();
    }
}
