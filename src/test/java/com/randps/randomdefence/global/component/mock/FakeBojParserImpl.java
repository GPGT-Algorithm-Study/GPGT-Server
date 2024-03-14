package com.randps.randomdefence.global.component.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.global.component.crawler.dto.BojProblemPair;
import com.randps.randomdefence.global.component.parser.Parser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeBojParserImpl implements Parser {

  private List<Object> solvedProblems;

  private LocalDateTime startOfActiveDay;

  public FakeBojParserImpl(List<Object> solvedProblems) {
    this.solvedProblems = solvedProblems;
  }

  public void setSolvedProblems(List<Object> solvedProblems) {
    this.solvedProblems = solvedProblems;
  }

  @Override
  public List<Object> getSolvedProblemList(String userName) throws JsonProcessingException {
    List<Object> todaySolvedProblems = new ArrayList<>();
    for (Object solvedProblem : solvedProblems) {
      String datetimeString = ((BojProblemPair) solvedProblem).getDateTime();
      LocalDateTime currentTime = LocalDateTime.of(Integer.parseInt(datetimeString.substring(0, 4)),
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

  @Override
  public void setStartOfActiveDay(LocalDateTime startOfActiveDay) {
    this.startOfActiveDay = startOfActiveDay;
  }

}
