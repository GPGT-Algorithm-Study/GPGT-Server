package com.randps.randomdefence.domain.problem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.randps.randomdefence.global.component.query.Query;
import com.randps.randomdefence.global.component.query.SolvedacQueryImpl;
import com.randps.randomdefence.domain.problem.domain.Problem;
import com.randps.randomdefence.domain.problem.domain.ProblemRepository;
import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    /*
     * solvedac의 문제를 내부 DB에서 불러온다.
     */
    @Transactional
    public ProblemDto findProblem(Integer problemId) {
        Optional<Problem> problem = problemRepository.findByProblemId(problemId);

        if (problem.isPresent()) {
            return problem.get().toDto();
        } else if (problemId == 0) {
            return new ProblemDto();
        }
        return scrapProblem(problemId).toDto();
    }

    /*
     * solvedac의 문제를 크롤링 후, DB에 저장한다.
     */
    @Transactional
    public Problem scrapProblem(Integer problemId) {
        Query query = new SolvedacQueryImpl();

        query.setDomain("https://solved.ac/api/v3/problem/show");

        query.setParam("problemId", problemId);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(query.getQuery(), JsonNode.class);
        JsonNode problemInfo = response.getBody();

        Problem problem = Problem.builder()
                .problemId(problemInfo.path("problemId").asInt())
                .titleKo(problemInfo.path("titleKo").asText())
                .isSolvable(problemInfo.path("isSolvable").asBoolean())
                .isPartial(problemInfo.path("isPartial").asBoolean())
                .acceptedUserCount(problemInfo.path("acceptedUserCount").asInt())
                .level(problemInfo.path("level").asInt())
                .votedUserCount(problemInfo.path("votedUserCount").asInt())
                .sprout(problemInfo.path("sprout").asBoolean())
                .givesNoRating(problemInfo.path("givesNoRating").asBoolean())
                .isLevelLocked(problemInfo.path("isLevelLocked").asBoolean())
                .averageTries(problemInfo.path("averageTries").asText())
                .official(problemInfo.path("official").asBoolean())
                .tags(makeSubJsonTag(problemInfo.path("tags")))
                .build();

        problemRepository.save(problem);

        return problem;
    }

    /*
     * Json의 Tag부분을 파싱한다.
     */
    public ArrayList<String> makeSubJsonTag(JsonNode jsonNode) {
        ArrayList<String> subTags = new ArrayList<String>();
        int size = jsonNode.size();

        for (int i = 0; i < size; i++) {
            subTags.add(jsonNode.path(i).path("displayNames").path(0).path("name").asText());
        }
        return subTags;
    }
}
