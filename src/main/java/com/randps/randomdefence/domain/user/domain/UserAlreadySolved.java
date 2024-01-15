package com.randps.randomdefence.domain.user.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import java.util.List;
import javax.persistence.ElementCollection;
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
    public UserAlreadySolved(Long id, String bojHandle, List<Integer> alreadySolvedList) {
        this.id = id;
        this.bojHandle = bojHandle;
        this.alreadySolvedList = alreadySolvedList;
    }

    public void setAlreadySolvedList(List<Integer> alreadySolvedList) {
        this.alreadySolvedList = alreadySolvedList;
    }
}
