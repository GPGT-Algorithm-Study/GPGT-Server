package com.randps.randomdefence.domain.recommendation.service;

import com.randps.randomdefence.domain.problem.dto.ProblemDto;
import com.randps.randomdefence.domain.problem.dto.ProblemSolveJudgedDto;
import com.randps.randomdefence.domain.problem.service.ProblemService;
import com.randps.randomdefence.domain.user.domain.UserAlreadySolved;
import com.randps.randomdefence.domain.user.service.port.UserAlreadySolvedRepository;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class RecommendationAlreadySolvedService {

    private final ProblemService problemService;

    private final UserAlreadySolvedRepository userAlreadySolvedRepository;

    @Transactional
    public ProblemSolveJudgedDto findOneRandomSolvedProblem(String bojHandle) {
        UserAlreadySolved userAlreadySolved = userAlreadySolvedRepository.findByBojHandle(bojHandle).orElseThrow(() -> new NoSuchElementException("유저가 기존에 푼 문제가 존재하지 않습니다."));
        List<Integer> solvedProblems = userAlreadySolved.getAlreadySolvedList();
        ProblemDto problemDto = problemService.findProblem(solvedProblems.get((int)Math.round(Math.random() * (solvedProblems.size() - 1))));
        return new ProblemSolveJudgedDto(problemDto, true);
    }

    @Transactional
    public ResponseEntity<String> findRecommendProblems(String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"title\": ");
        sb.append("\"");
        sb.append(title);
        sb.append("\"");
        sb.append("}");
        String body = sb.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange(
                "https://univps.kr/ml/find_similar_question", //{요청할 서버 주소}
                HttpMethod.POST, //{요청할 방식}
                entity, // {요청할 때 보낼 데이터}
                String.class // {요청시 반환되는 데이터 타입}
        );
        return response;
    }

    @Transactional
    public ResponseEntity<String> findClusterProblems(String bojHandle) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"name\": ");
        sb.append("\"");
        sb.append(bojHandle);
        sb.append("\"");
        sb.append("}");
        String body = sb.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange(
                "https://univps.kr/ml/recommend_problems", //{요청할 서버 주소}
                HttpMethod.POST, //{요청할 방식}
                entity, // {요청할 때 보낼 데이터}
                String.class // {요청시 반환되는 데이터 타입}
        );
        return response;
    }
}
