package com.soso.domain.controller;

import com.soso.common.aop.jwt.JwtValidationAOP;
import com.soso.domain.dto.DataDTO;
import com.soso.domain.dto.ResponseDatasDTO;
import com.soso.domain.service.itf.DataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@Tag(name = "데이터 관련 API", description = "Swagger 데이터 테스트용 API")
@JwtValidationAOP
@RequestMapping("/data")
@RestController
public class DataController {

    @Autowired
    DataService dataService;

    @GetMapping("/datas")
    public ResponseEntity<?> findDatasByLoginMember(@CookieValue String sosoJwtToken) {
        return new ResponseEntity<>(dataService.findDatasByLoginMember(sosoJwtToken), HttpStatus.OK);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<?> findDataByDataId(@CookieValue String sosoJwtToken, @PathVariable("id") int id) {
        return new ResponseEntity<>(dataService.findDataByDataId(sosoJwtToken, id), HttpStatus.OK);
    }

    @PostMapping("/data")
    public ResponseEntity<?> registerDataByFromMemberId(@CookieValue String sosoJwtToken, @RequestBody HashMap<String, String> reqData) {
        return new ResponseEntity<>(dataService.registerDataByFromMemberId(sosoJwtToken, reqData), HttpStatus.OK);
    }

    @PatchMapping("/data")
    public ResponseEntity<?> updateDataByFromMemberId(@CookieValue String sosoJwtToken, @RequestBody DataDTO dataDTO) {
        return new ResponseEntity<>(dataService.updateDataByFromMemberId(sosoJwtToken, dataDTO), HttpStatus.OK);
    }

    @PatchMapping("/datas")
    public ResponseEntity<?> updateDataByFromMemberId(@CookieValue String sosoJwtToken, @RequestBody ResponseDatasDTO responseDatasDTO) {
        return new ResponseEntity<>(dataService.updateDatasByFromMemberId(sosoJwtToken, responseDatasDTO), HttpStatus.OK);
    }
}
