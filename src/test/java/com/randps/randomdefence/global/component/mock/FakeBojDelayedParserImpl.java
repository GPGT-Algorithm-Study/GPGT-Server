package com.randps.randomdefence.global.component.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.parser.Parser;
import java.util.List;

public class FakeBojDelayedParserImpl implements Parser {

  private List<Object> solvedProblems;

  private boolean isDelayed = true;

  public void delayOn() {
    isDelayed = true;
  }

  public void delayOff() {
    isDelayed = false;
  }

  public FakeBojDelayedParserImpl(List<Object> solvedProblems) {
    this.solvedProblems = solvedProblems;
  }

  public void setSolvedProblems(List<Object> solvedProblems) {
    this.solvedProblems = solvedProblems;
  }

  @Override
  public List<Object> getSolvedProblemList(String userName) throws JsonProcessingException {
    if (!isDelayed)
      return solvedProblems;
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new RuntimeException("error occurred while waiting for 4 seconds.");
    }
    return solvedProblems;
  }

}
