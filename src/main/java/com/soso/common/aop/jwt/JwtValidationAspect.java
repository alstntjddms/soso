package com.soso.common.aop.jwt;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.JWT.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class JwtValidationAspect {

    private static final String TOKEN_NAME = "sosoJwtToken";

    @Before("@within(JwtValidationAOP) || @annotation(JwtValidationAOP)")
    public void validateJwt() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            throw new CustomException(ExceptionStatus.COOKIES_NOT_FOUND);
        }

        for (Cookie cookie : cookies) {
            if (TOKEN_NAME.equals(cookie.getName())) {
                if (!JwtUtils.validateJwtToken(cookie.getValue())) {
                    throw new CustomException(ExceptionStatus.JWT_FAIL_VALIDATE);
                }
                return;
            }
        }
        throw new CustomException(ExceptionStatus.COOKIES_NOT_FOUND);

    }
}
