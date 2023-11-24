package com.soso.login.controller;

import com.soso.login.dto.LoginMemberDTO;
import com.soso.login.dto.MemberDTO;
import com.soso.login.service.itf.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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
    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>("login test", HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginMemberDTO loginMemberDTO, HttpServletResponse res) throws Exception {
        System.out.println("loginMemberDTO = " + loginMemberDTO);
        return new ResponseEntity<>(loginService.loginMember(loginMemberDTO, res), HttpStatus.OK);
    }

}
