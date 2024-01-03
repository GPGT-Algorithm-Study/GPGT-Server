package com.randps.randomdefence.domain.boolshit.domain;

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
@Table(name = "RD_BOOLSHIT")
@Entity
public class Boolshit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String bojHandle;

    @Builder
    public Boolshit(Long id, String message, String bojHandle) {
        this.id = id;
        this.message = message;
        this.bojHandle = bojHandle;
    }
}
