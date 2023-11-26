package com.soso.domain.controller;

import com.soso.common.aop.jwt.JwtValidationAOP;
import com.soso.domain.service.itf.DomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@Tag(name = "도메인 관련 API", description = "Swagger 도메인 테스트용 API")
@JwtValidationAOP
@RequestMapping("/domain")
@RestController
public class DomainController {

    @Autowired
    DomainService domainService;

    @Operation(summary = "도메인 테스트 API", description = "도메인 관련 테스트용 API입니다.")
    @GetMapping
    public ResponseEntity<?> domain(){
            return new ResponseEntity<>(domainService.domain(), HttpStatus.OK);
    }

    @GetMapping("/teams")
    public ResponseEntity<?> findTeamsByLoginMember(@CookieValue String sosoJwtToken) {
        return new ResponseEntity<>(domainService.findTeamsByLoginId(sosoJwtToken), HttpStatus.OK);
    }

    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@CookieValue String sosoJwtToken, @RequestBody HashMap<String, String> reqData) {
        return new ResponseEntity<>(domainService.createTeam(sosoJwtToken, reqData), HttpStatus.OK);
    }

}
