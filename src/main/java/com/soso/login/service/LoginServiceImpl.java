package com.soso.login.service;

import com.soso.login.dto.LoginMemberDTO;
import com.soso.login.repository.itf.LoginRAO;
import com.soso.login.service.itf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginRAO rao;

    @Override
    public List<HashMap> test() {
        return rao.test();
    }

    @Override
    public void loginMember(LoginMemberDTO loginMemberDTO) {

    }
}
