package com.randps.randomdefence.global.component.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.parser.Parser;
import java.util.ArrayList;
import java.util.List;

public class FakeParserImpl implements Parser {

    @Override
    public List<Object> getSolvedProblemList(String userName) throws JsonProcessingException {
        return new ArrayList<>();
    }
}
