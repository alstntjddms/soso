package com.soso.login.controller;

import com.soso.login.service.itf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(loginService.test(), HttpStatus.OK);
    }

}
