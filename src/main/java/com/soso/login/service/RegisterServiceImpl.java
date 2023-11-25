package com.soso.login.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.hash.SHA256;
import com.soso.common.utils.pattern.Validate;
import com.soso.login.dto.RegisterMemberDTO;
import com.soso.login.dto.CertifiedCodeDTO;
import com.soso.login.repository.CertifiedCodeRepository;
import com.soso.login.repository.itf.RegisterRAO;
import com.soso.login.service.itf.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterRAO rao;

    @Autowired
    CertifiedCodeRepository certifiedCodeRepository;

    @Autowired
    SHA256 sha256;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public boolean checkLoginIdDuplicated(HashMap<String, String> reqData) {
        // loginId 6자 이하 OR loginId 30자 이상
        if(reqData.get("loginId").length() < 6 || reqData.get("loginId").length() > 30){
            throw new CustomException(ExceptionStatus.LOGIN_ID_LENGTH);
        }

        // loginId 중복 검사
        if(!duplicatedCheckLoginId(reqData.get("loginId"))){
            throw new CustomException(ExceptionStatus.LOGIN_ID_DUPLICATED);
        }
        return true;
    }

    @Override
    public boolean checkEmailDuplicated(HashMap<String, String> reqData) {
        // 이메일 유효성 검사
        if(!Validate.isEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_WRONG_PATTERN);
        }
        
        // 이메일 중복 검사
        if(!duplicatedCheckEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }
        return true;
    }

    @Override
    public boolean sendCertifiedCodeToMail(HashMap<String, String> reqData) {
        // 이메일 중복체크
        if(!duplicatedCheckEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }

        // 이메일 유효성 검사
        if(!Validate.isEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_WRONG_PATTERN);
        }

        // 무작위 코드를 생성
        String certifiedCode = generateCertifiedCode();
        System.out.println("certifiedCode = " + certifiedCode);

        // 무작위 코드를 이메일로 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("soso.project@gmail.com");
        message.setTo(reqData.get("email"));
        message.setSubject("인증메일 테스트");
        message.setText(certifiedCode);
        javaMailSender.send(message);

        // 무작위 코드를 repository에 저장
        certifiedCodeRepository.repository.add(
                new CertifiedCodeDTO(reqData.get("email"), certifiedCode, new Timestamp(System.currentTimeMillis())));
        return true;
    }

    @Override
    public boolean checkEmailFromCertifiedCode(HashMap<String, String> reqData) {
        if(!checkCertifiedCodeToEmail(reqData.get("email"), reqData.get("certifiedCode"))){
            throw new CustomException(ExceptionStatus.CERTIFIED_CODE_WRONG);
        }
        return true;
    }

    @Override
    public synchronized String registerMember(RegisterMemberDTO registerMemberDTO) throws Exception {
        // 아이디 중복체크
        if(!duplicatedCheckLoginId(registerMemberDTO.getLoginId())){
            throw new CustomException(ExceptionStatus.LOGIN_ID_DUPLICATED);
        }

        // 이메일 중복체크
        if(!duplicatedCheckEmail(registerMemberDTO.getEmail())){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }

        // 인증코드 체크
        if(!checkCertifiedCodeToEmail(registerMemberDTO.getEmail(), registerMemberDTO.getCertifiedCode())){
            throw new CustomException(ExceptionStatus.CERTIFIED_CODE_WRONG);
        }

        // 아이디, 비밀번호, 이메일 유효성 검사 ex) 아이디 6자이상 영어, 비밀번호 8자 이상
        // loginId 6자 이하 OR loginId 30자 이상
        if(registerMemberDTO.getLoginId().length() < 6 || registerMemberDTO.getLoginId().length() > 30){
            throw new CustomException(ExceptionStatus.LOGIN_ID_LENGTH);
        }
        // password 8자 이하
        if(registerMemberDTO.getPassword().length() < 8 ){
            throw new CustomException(ExceptionStatus.PASSWORD_LENGTH);
        }
        // 이메일 유효성 검사
        if(!Validate.isEmail(registerMemberDTO.getEmail())){
            throw new CustomException(ExceptionStatus.EMAIL_WRONG_PATTERN);
        }

        // password = hash(평문 + salt) 설정
        registerMemberDTO.setSalt(sha256.generateSalt());
        registerMemberDTO.setPassword(sha256.hash(registerMemberDTO.getPassword(), registerMemberDTO.getSalt()));

        rao.registerMember(registerMemberDTO);

        return registerMemberDTO.getLoginId();
    }

    public boolean duplicatedCheckLoginId(String loginId) {
        if(rao.checkLoginIdDuplicated(loginId) == null){
            return true;
        }
        return false;
    }

    public boolean duplicatedCheckEmail(String email) {
        if(rao.checkEmailDuplicated(email) == null){
            return true;
        }
        return false;
    }

    public boolean checkCertifiedCodeToEmail(String email, String certifiedCode) {
        if(certifiedCodeRepository.checkEMailFromCertifiedCode(email, certifiedCode)){
            return true;
        }
        return false;
    }

    public static String generateCertifiedCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

        return IntStream.range(0, 10)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
