package com.soso.common.aop.exception;


import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException extends RuntimeException{
    ExceptionStatus exceptionStatus;
    public CustomException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
