package com.soso.login.controller;

import com.soso.login.service.itf.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 관련 API", description = "Swagger 로그인 테스트용 API")
@RequestMapping("/register")
@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping("check-LoginIdDuplicated")
    public ResponseEntity<?> checkLoginIdDuplicated(@RequestBody String loginId){
        return new ResponseEntity<>(registerService.checkLoginIdDuplicated(loginId), HttpStatus.OK);
    }

    @PostMapping("/send-CertifiedCodeToMail")
    public ResponseEntity<?> sendCertifiedCodeToMail(@RequestBody String email){
        return new ResponseEntity<>(registerService.sendCertifiedCodeToMail(email), HttpStatus.OK);
    }

}
