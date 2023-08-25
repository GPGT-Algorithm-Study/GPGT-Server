package com.randps.randomdefence.domain.boolshit.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_BOOLSHIT")
@Entity
public class Boolshit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String bojHandle;

    @Builder
    public Boolshit(String message, String bojHandle) {
        this.message = message;
        this.bojHandle = bojHandle;
    }
}
