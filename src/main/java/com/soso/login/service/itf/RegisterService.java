package com.soso.login.service.itf;

import com.soso.login.dto.RegisterMemberDTO;

import java.util.HashMap;

public interface RegisterService {

    /**
     * 중복된 아이디가 있는지 확인한다.
     * @param reqData
     * @return
     */
    public boolean checkLoginIdDuplicated(HashMap<String, String> reqData);

    /**
     * 중복된 이메일이 reqData
     * @return
     */
    public boolean checkEmailDuplicated(HashMap<String, String> reqData);


    /**
     * 이메일 인증번호를 전송한다.
     * @param reqData
     * @return
     */
    public boolean sendCertifiedCodeToMail(HashMap<String, String> reqData);

    /**
     * 이메일 인증번호를 검증한다.
     * @param reqData
     * @return
     */
    public boolean checkEmailFromCertifiedCode(HashMap<String, String > reqData);

    /**
     * 멤버를 등록한다.
     * @param registerMemberDTO
     * @return loginId
     */
    public String registerMember(RegisterMemberDTO registerMemberDTO) throws Exception;
}
