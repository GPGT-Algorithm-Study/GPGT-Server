package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.List;

public interface Parser {
    List<Object> getSolvedProblemList(String userName) throws JsonProcessingException;

    void setStartOfActiveDay(LocalDateTime startOfActiveDay);
}
