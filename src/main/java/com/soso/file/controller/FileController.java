package com.soso.file.controller;

import com.soso.common.aop.jwt.JwtValidationAOP;
import com.soso.file.service.itf.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "파일 관련 API", description = "Swagger 파일 테스트용 API")
//@JwtValidationAOP
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    FileService fileService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.uploadFile(file), HttpStatus.OK);
    }

//    @GetMapping("/download")
//    public ResponseEntity<?> downloadFile(@RequestParam("file") MultipartFile file) {
//        return new ResponseEntity<>(fileService.downloadFile(), HttpStatus.OK);
//    }
}
