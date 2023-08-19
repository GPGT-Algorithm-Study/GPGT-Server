package com.randps.randomdefence.domain.item.domain;

import com.randps.randomdefence.domain.item.dto.ItemDto;
import com.randps.randomdefence.global.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "RD_ITEM")
@Entity
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer itemValue;

    private Integer maxItemCount;

    @Builder
    public Item(String name, String description, Integer itemValue, Integer maxItemCount) {
        this.name = name;
        this.description = description;
        this.itemValue = itemValue;
        this.maxItemCount = maxItemCount;
    }

    public ItemDto toDto() {
        return ItemDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .itemValue(this.itemValue)
                .maxItemCount(this.maxItemCount)
                .build();
    }
}
