package com.randps.randomdefence.component.query;

import lombok.Builder;
import lombok.Data;

@Data
public class Pair {
    String key;
    String value;

    @Builder
    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
