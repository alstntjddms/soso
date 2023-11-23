package com.soso.domain.controller;

import com.soso.domain.service.itf.DomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "도메인 관련 API", description = "Swagger 도메인 테스트용 API")
@RequestMapping("/domain")
@RestController
public class DomainController {

    @Autowired
    DomainService domainService;

    @Operation(summary = "도메인 테스트 API", description = "도메인 관련 테스트용 API입니다.")
    @GetMapping("/domain")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(domainService.domain(), HttpStatus.OK);
    }
}
