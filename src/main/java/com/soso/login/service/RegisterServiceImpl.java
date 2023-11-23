package com.soso.login.service;

import com.soso.login.dto.RegisterMemberDTO;
import com.soso.login.repository.CertifiedCodeDTO;
import com.soso.login.repository.CertifiedCodeRepository;
import com.soso.login.repository.itf.RegisterRAO;
import com.soso.login.service.itf.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.HashMap;

public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterRAO rao;

    @Override
    public boolean checkIdDuplicated(String loginId) {
        return false;
    }

    @Override
    public boolean sendCertifiedToMail(String email) {
        // 무작위 코드를 생성

        // 무작위 코드를 이메일로 전송

        // 무작위 코드를 repository에 저장
        CertifiedCodeRepository.repository.add(
                new CertifiedCodeDTO(email, "test", new Timestamp(System.currentTimeMillis())));

        return false;
    }

    @Override
    public boolean checkMailFromCertifiedCode(String email, String certifiedCode) {
        return false;
    }

    @Override
    public String registerMember(RegisterMemberDTO registerMemberDTO) {
        return null;
    }
}
