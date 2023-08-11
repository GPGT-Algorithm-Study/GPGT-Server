package com.randps.randomdefence.user.controller;

import com.randps.randomdefence.user.dto.UserInfoResponse;
import com.randps.randomdefence.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    //TODO: useradd, userdel은 jwt 토큰을 헤더에 넣어야지 접근가능하게 설정
    @PostMapping("/add")
    public HttpStatus userAdd(@Param("bojHandle") String bojHandle, @Param("notionId") String notionId, @Param("manager") Long manager) {
        userService.save(bojHandle, notionId, manager);

        return HttpStatus.OK;
    }

    @DeleteMapping("/del")
    public HttpStatus userDel(@Param("bojHandle") String bojHandle) {
        userService.delete(bojHandle);

        return HttpStatus.OK;
    }

    @GetMapping("/info")
    public UserInfoResponse info(@Param("bojHandle") String bojHandle) {
        return userService.getInfo(bojHandle);
    }
}
