package com.randps.randomdefence.global.component.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.parser.Parser;
import java.util.List;

public class FakeBojParserImpl implements Parser {

  private List<Object> solvedProblems;

  public FakeBojParserImpl(List<Object> solvedProblems) {
    this.solvedProblems = solvedProblems;
  }

  public void setSolvedProblems(List<Object> solvedProblems) {
    this.solvedProblems = solvedProblems;
  }

  @Override
  public List<Object> getSolvedProblemList(String userName) throws JsonProcessingException {
    return solvedProblems;
  }

}
