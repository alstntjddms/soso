package com.soso.login.service.itf;

import com.soso.login.dto.LoginMemberDTO;
import com.soso.login.dto.RegisterMemberDTO;

import java.util.HashMap;
import java.util.List;

public interface LoginService {
    /**
     * 테스트용
     * @return
     */
    public List<HashMap> test();

    public void loginMember (LoginMemberDTO loginMemberDTO);

}
