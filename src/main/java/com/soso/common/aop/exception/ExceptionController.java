package com.soso.common.aop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.Arrays;

@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> clientBadRequest(CustomException ce){
        log.warn("[예외명] : " + ce.exceptionStatus.getStatus() + " [메시지] : " + ce.exceptionStatus.getMessage()
                    + " [발생 시간] : " + new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(
                        ce.exceptionStatus.getStatus(),ce.exceptionStatus.getMessage(), new Timestamp(System.currentTimeMillis())
                ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionRequest(Exception e){
        log.warn("[예외명] : " + "알수 없는 에러 " + "[메시지] : " + e.getCause().getMessage()
                    + " [발생 시간] : " + new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(
                        "알수 없는 에러", e.getMessage(), new Timestamp(System.currentTimeMillis())
                ), HttpStatus.BAD_REQUEST);
    }

}
