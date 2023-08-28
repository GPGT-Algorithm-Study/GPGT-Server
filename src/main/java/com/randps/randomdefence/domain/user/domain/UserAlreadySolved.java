package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "RD_USER_ALREADY_SOLVED")
@Entity
public class UserAlreadySolved extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    @ElementCollection
    private List<Integer> alreadySolvedList;

    @Builder
    public UserAlreadySolved(String bojHandle, List<Integer> alreadySolvedList) {
        this.bojHandle = bojHandle;
        this.alreadySolvedList = alreadySolvedList;
    }

    public void setAlreadySolvedList(List<Integer> alreadySolvedList) {
        this.alreadySolvedList = alreadySolvedList;
    }
}
