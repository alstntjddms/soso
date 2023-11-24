package com.soso.common.aop.exception;

public class CustomException extends RuntimeException{
    ExceptionStatus exceptionStatus;
    public CustomException(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}
