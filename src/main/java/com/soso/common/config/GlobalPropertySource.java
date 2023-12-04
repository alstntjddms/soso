package com.soso.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource( value = "file:c:/config/soso-config.properties" )
public class GlobalPropertySource {

    // DB설정 불러오기
    @Getter
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Getter
    @Value("${spring.datasource.url}")
    private String url;

    @Getter
    @Value("${spring.datasource.username}")
    private String username;

    @Getter
    @Value("${spring.datasource.password}")
    private String password;

    // 메일 설정 불러오기
    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private String mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.datasource.password}")
    private String mailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailEnable;

    // 파일저장 관련 설정
//    @Value("${spring.servlet.multipart.enabled}")
//    private String multipartEnabled;
//
//    @Value("${spring.servlet.multipart.file-size-threshold}")
//    private String multipartFileSizeThreshold;
//
//    @Value("${spring.servlet.multipart.max-file-size}")
//    private String multipartMaxFileSize;
//
//    @Value("${spring.servlet.multipart.max-request-size}")
//    private String multipartMaxRequestSize;

}