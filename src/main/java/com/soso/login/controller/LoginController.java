package com.soso.login.controller;

import com.soso.common.aop.jwt.JwtValidationAOP;
import com.soso.login.dto.LoginMemberDTO;
import com.soso.login.service.itf.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 관련 API", description = "Swagger 로그인 테스트용 API")
@RequestMapping("/login")
@RestController
@CrossOrigin(originPatterns="*", allowCredentials = "true")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Operation(summary = "로그인 테스트 API", description = "로그인 관련 테스트용 API입니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginMemberDTO loginMemberDTO, HttpServletResponse res) throws Exception {
        return new ResponseEntity<>(loginService.loginMember(loginMemberDTO, res), HttpStatus.OK);
    }

    @JwtValidationAOP
    @GetMapping("/member")
    public ResponseEntity<?> findLoginMember(@CookieValue String sosoJwtToken) {
        return new ResponseEntity<>(loginService.findLoginMember(sosoJwtToken), HttpStatus.OK);
    }

}
