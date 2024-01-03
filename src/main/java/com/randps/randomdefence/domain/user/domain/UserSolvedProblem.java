package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.domain.user.dto.SolvedProblemDto;
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

    private String language;

    @Builder
    public UserSolvedProblem(Long id, String bojHandle, Integer problemId, String title, String dateTime, String language) {
        this.id = id;
        this.bojHandle = bojHandle;
        this.problemId = problemId;
        this.title = title;
        this.dateTime = dateTime;
        this.language = language;
    }

    public SolvedProblemDto toDto() {
        return SolvedProblemDto.builder()
                .problemId(this.getProblemId())
                .title(this.getTitle())
                .dateTime(this.getDateTime())
                .language(this.getLanguage())
                .build();
    }
}
