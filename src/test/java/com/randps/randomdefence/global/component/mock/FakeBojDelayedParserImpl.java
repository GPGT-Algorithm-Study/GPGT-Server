package com.randps.randomdefence.global.component.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.parser.Parser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeBojDelayedParserImpl implements Parser {

  private List<Object> solvedProblems;

  private LocalDateTime startOfActiveDay;

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
    if (!isDelayed) {
      List<Object> todaySolvedProblems = new ArrayList<>();
      for (Object solvedProblem : solvedProblems) {
        // 형식 : 2022-01-01T00:00:00
        String datetimeString = ((BojProblemPair) solvedProblem).getDateTime();
        LocalDateTime currentTime = LocalDateTime.of(
            Integer.parseInt(datetimeString.substring(0, 4)),
            Integer.parseInt(datetimeString.substring(5, 7)),
            Integer.parseInt(datetimeString.substring(8, 10)),
            Integer.parseInt(datetimeString.substring(11, 13)),
            Integer.parseInt(datetimeString.substring(14, 16)),
            Integer.parseInt(datetimeString.substring(17, 19)));
        if (startOfActiveDay.isBefore(currentTime) && (startOfActiveDay.plusDays(1))
            .isAfter(currentTime)) {
          todaySolvedProblems.add(solvedProblem);
        }
      }
      return todaySolvedProblems;
    }
    try {
      Thread.sleep(1000 * 2);
    } catch (InterruptedException e) {
      throw new RuntimeException("error occurred while waiting");
    }
    List<Object> todaySolvedProblems = new ArrayList<>();
    for (Object solvedProblem : solvedProblems) {
      // 형식 : 2022-01-01 00:00:00
      String datetimeString = ((BojProblemPair) solvedProblem).getDateTime();
      LocalDateTime currentTime = LocalDateTime.of(Integer.parseInt(datetimeString.substring(0, 4)),
          Integer.parseInt(datetimeString.substring(5, 7)),
          Integer.parseInt(datetimeString.substring(8, 10)),
          Integer.parseInt(datetimeString.substring(11, 13)),
          Integer.parseInt(datetimeString.substring(14, 16)),
          Integer.parseInt(datetimeString.substring(17, 19)));
      System.out.println("currentTime = " + currentTime);
      System.out.println("startOfActiveDay = " + startOfActiveDay);
      System.out.println("startOfActiveDay.plusDays(1) = " + startOfActiveDay.plusDays(1));
      if (startOfActiveDay.isBefore(currentTime) && (startOfActiveDay.plusDays(1))
          .isAfter(currentTime)) {
        todaySolvedProblems.add(solvedProblem);
        System.out.println("YES solvedProblem = " + solvedProblem);
      } else {
        System.out.println("NO solvedProblem = " + solvedProblem);
      }
    }
    return todaySolvedProblems;
  }

  @Override
  public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
    this.startOfActiveDay = startOfActiveDay;
  }

}
