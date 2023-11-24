package com.soso.common.aop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private String name;
    private String message;
    private Timestamp errorDate;
}
