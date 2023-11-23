package com.soso.login.controller;

import com.soso.login.service.itf.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 관련 API", description = "Swagger 로그인 테스트용 API")
@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @Operation(summary = "로그인 테스트 API", description = "로그인 관련 테스트용 API입니다.")
    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>("login test", HttpStatus.OK);
    }

}
