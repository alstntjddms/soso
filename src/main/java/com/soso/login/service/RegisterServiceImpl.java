package com.soso.login.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.hash.SHA256;
import com.soso.login.dto.RegisterMemberDTO;
import com.soso.login.dto.CertifiedCodeDTO;
import com.soso.login.repository.CertifiedCodeRepository;
import com.soso.login.repository.itf.RegisterRAO;
import com.soso.login.service.itf.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;


@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterRAO rao;

    @Autowired
    CertifiedCodeRepository certifiedCodeRepository;

    @Autowired
    SHA256 sha256;

    @Override
    public boolean checkLoginIdDuplicated(String loginId) {
        // DB의 데이터와 비교해 아이디 중복체크
        if(false){
            throw new CustomException(ExceptionStatus.LOGIN_ID_DUPLICATED);
        }
        return false;
    }

    @Override
    public boolean sendCertifiedCodeToMail(String email) {
        // 이메일 중복체크
        if(false){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }
        // 무작위 코드를 생성

        // 무작위 코드를 이메일로 전송

        // 무작위 코드를 repository에 저장
        certifiedCodeRepository.repository.add(
                new CertifiedCodeDTO(email, "무작위 코드", new Timestamp(System.currentTimeMillis())));
        return false;
    }

    @Override
    public boolean checkMailFromCertifiedCode(String email, String certifiedCode) {
        return certifiedCodeRepository.checkMailFromCertifiedCode(email, certifiedCode);
    }

    @Override
    public String registerMember(RegisterMemberDTO registerMemberDTO) throws Exception {
        // 아이디 중복체크
        checkLoginIdDuplicated(registerMemberDTO.getLoginId());

        // 이메일 중복체크
        if(false){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }

        // 아이디, 비밀번호 유효성 검사 ex) 아이디 6자이상 영어, 비밀번호 8자 이상

        // 비밀번호(평문 + salt) 해시
        String salt = sha256.generateSalt();
        registerMemberDTO.setPassword(sha256.hash(registerMemberDTO.getPassword(), salt));

        // db에 저장 아이디, 비밀번호, salt, name, email 등

        return null;
    }
}
