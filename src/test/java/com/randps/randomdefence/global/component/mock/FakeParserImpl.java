package com.randps.randomdefence.global.component.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.parser.Parser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeParserImpl implements Parser {

    private LocalDateTime startOfActiveDay;

    @Override
    public List<Object> getSolvedProblemList(String userName) throws JsonProcessingException {
        return new ArrayList<>();
    }

    @Override
    public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
        this.startOfActiveDay = startOfActiveDay;
    }
}
