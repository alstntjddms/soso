package com.soso.common.aop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.Arrays;

@RestControllerAdvice
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> clientBadRequest(CustomException ce){
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(
                        ce.exceptionStatus.getStatus(),ce.exceptionStatus.getMessage(), new Timestamp(System.currentTimeMillis())
                ), HttpStatus.BAD_REQUEST);
    }

}
