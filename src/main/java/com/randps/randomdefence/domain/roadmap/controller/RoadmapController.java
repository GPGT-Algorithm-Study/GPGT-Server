package com.randps.randomdefence.domain.roadmap.controller;

import com.randps.randomdefence.domain.roadmap.dto.RoadmapDto;
import com.randps.randomdefence.domain.roadmap.service.RoadmapService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roadmap")
public class RoadmapController {

    private final RoadmapService roadmapService;

    /**
     * 로드맵을 생성한다.
     */
    @PostMapping("/create")
    public RoadmapDto create(@Param("lectureId") String lectureId, @Param("name") String name, @Param("classification") String classification, @Param("description") String description, @Param("difficulty") String difficulty) {
        return roadmapService.save(lectureId, name, classification, description, difficulty);
    }

    /**
     * 특정 로드맵을 수정한다.
     */
    @PutMapping("/update")
    public RoadmapDto update(@Param("id") Long id, @Param("lectureId") String lectureId, @Param("name") String name, @Param("classification") String classification, @Param("description") String description, @Param("difficulty") String difficulty) {
        return roadmapService.update(id, lectureId, name, classification, description, difficulty);
    }

    /**
     * 특정 로드맵을 삭제한다.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> delete(@Param("id") Long id) {
        roadmapService.delete(id);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "요청이 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}
