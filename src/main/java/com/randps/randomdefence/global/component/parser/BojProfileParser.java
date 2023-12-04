package com.randps.randomdefence.global.component.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface BojProfileParser {
    List<Object> getBojUserProfile(String bojHandle) throws JsonProcessingException;
}
