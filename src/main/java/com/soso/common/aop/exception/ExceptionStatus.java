package com.soso.common.aop.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionStatus {
    COOKIES_NOT_FOUND("쿠키가 없습니다."),
    JWT_FAIL_VALIDATE("JWT 검증에 실패했습니다."),
    UNKNOWN_ERROR("알수 없는 에러 발생"),
    LOGIN_ID_DUPLICATED("로그인 아이디 중복됨"),
    EMAIL_DUPLICATED("이메일 중복됨");

    private final String message;

    public String getStatus(){
        return name();
    }

}