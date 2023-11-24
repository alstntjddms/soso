package com.soso.login.controller;

import com.soso.login.dto.LoginMemberDTO;
import com.soso.login.dto.RegisterMemberDTO;
import com.soso.login.service.itf.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Tag(name = "로그인 관련 API", description = "Swagger 로그인 테스트용 API")
@RequestMapping("/register")
@RestController
@CrossOrigin(originPatterns="*", allowCredentials = "true")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping("/check-LoginIdDuplicated")
    public ResponseEntity<?> checkLoginIdDuplicated(@RequestBody HashMap<String, String> reqData){
        return new ResponseEntity<>(registerService.checkLoginIdDuplicated(reqData), HttpStatus.OK);
    }

    @PostMapping("/send-CertifiedCodeToMail")
    public ResponseEntity<?> sendCertifiedCodeToMail(@RequestBody HashMap<String, String> reqData){
        return new ResponseEntity<>(registerService.sendCertifiedCodeToMail(reqData), HttpStatus.OK);
    }

    @PostMapping("/check-CertifiedCodeToEmail")
    public ResponseEntity<?> checkEmailFromCertifiedCode(@RequestBody HashMap<String, String> reqData){
        return new ResponseEntity<>(registerService.checkEmailFromCertifiedCode(reqData), HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<?> login(@RequestBody RegisterMemberDTO registerMemberDTO) throws Exception {
        System.out.println("registerMemberDTO = " + registerMemberDTO);
        return new ResponseEntity<>(registerService.registerMember(registerMemberDTO), HttpStatus.OK);
    }

}
