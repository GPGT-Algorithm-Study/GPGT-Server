package com.randps.randomdefence.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.randps.randomdefence.domain.user.service.UserSejongRegisterService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/sejong/register")
public class UserSejongRegisterController {

    private final UserSejongRegisterService userSejongRegisterService;

    /*
     * 유저의 아래 정보를 반환한다.
     * univps 가입 여부
     * 백준 새종대 학생 여부
     */
    @GetMapping("/valid/user")
    public ResponseEntity<Map<String, String>> validateUser(@Param("bojHandle") String bojHandle)
            throws JsonProcessingException {

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        if (userSejongRegisterService.isAlreadyExistUser(bojHandle)) {
            map.put("message", "이미 회원가입한 유저입니다.");
        }
        else if (!userSejongRegisterService.isSejongUser(bojHandle)) {
            map.put("message", "세종대학교 학생이 아닙니다.");
        }
        else {
            map.put("message", "회원가입 가능한 유저입니다.");
        }
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 인증 번호를 생성한다.
     */
    @PostMapping("/valid/code")
    public String makeValidateCode(@Param("bojHandle") String bojHandle) {
        return userSejongRegisterService.makeRegisterCode(bojHandle);
    }

    /*
     * 인증 번호를 검증한다.
     */
    @GetMapping("/valid/code")
    public ResponseEntity<Map<String, String>> makeValidation(@Param("bojHandle") String bojHandle) throws JsonProcessingException, IllegalAccessException {
        Boolean isValid = userSejongRegisterService.validBojUserMessage(bojHandle);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        if (isValid)
            map.put("message", "검증에 성공했습니다.");
        else
            map.put("message", "검증에 실패했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    /*
     * 회원을 등록한다.
     */
    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> registerUser(@Param("bojHandle") String bojHandle, @Param("password") String password)
            throws JsonProcessingException {

        userSejongRegisterService.registerUser(bojHandle, password);

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;

        Map<String, String> map = new HashMap<>();
        map.put("type", httpStatus.getReasonPhrase());
        map.put("code", "200");
        map.put("message", "회원가입에 성공했습니다.");
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}
