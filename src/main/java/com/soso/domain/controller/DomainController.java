package com.soso.domain.controller;

import com.soso.domain.service.itf.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/domain")
public class DomainController {

    @Autowired
    DomainService domainService;

    @GetMapping("/domain")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(domainService.domain(), HttpStatus.OK);
    }
}
