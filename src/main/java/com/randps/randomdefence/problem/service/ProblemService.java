package com.randps.randomdefence.problem.service;

import com.randps.randomdefence.problem.domain.Problem;
import com.randps.randomdefence.problem.domain.ProblemRepository;
import com.randps.randomdefence.user.dto.ProblemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    //TODO: 문제 정보를 받아올 때, 내부 DB에서 가져오고 내부 DB에 없다면 API를 호출해서 가져온 뒤, DB에 저장하고 반환한다.
    /*
     * solvedac의 문제를 내부 DB에서 불러온다.
     */
//    @Transactional
//    public ProblemDto findProblem(Integer problemId) {
//        Optional<Problem> problem = problemRepository.findByProblemId(problemId);
//
//        if ()
//    }

    /*
     * solvedac의 문제를 내부 DB에 스크랩 한다.
     */
//    @Transactional
//    public Problem scrapProblem(Integer problemId) {
//
//    }
}
