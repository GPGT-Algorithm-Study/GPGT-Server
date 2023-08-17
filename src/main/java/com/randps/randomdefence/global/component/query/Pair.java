package com.randps.randomdefence.global.component.query;

import lombok.Builder;
import lombok.Data;

@Data
public class Pair {
    String key;
    Object value;

    @Builder
    public Pair(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
