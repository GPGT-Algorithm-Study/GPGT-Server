package com.randps.randomdefence.domain.item.domain;

import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import javax.persistence.Entity;
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
@Table(name = "RD_USER_ITEM")
@Entity
public class UserItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bojHandle;

    private Integer count;

    @ManyToOne
    private Item item;

    @Builder
    public UserItem(Long id, String bojHandle, Integer count, Item item) {
        this.id = id;
        this.bojHandle = bojHandle;
        this.count = count;
        this.item = item;
    }

    public Boolean increaseCount() {
        if (this.count < item.getMaxItemCount()){
            this.count++;
            return true;
        }
        return false;
    }

    public void decreaseCount() {
        this.count--;
    }

}
