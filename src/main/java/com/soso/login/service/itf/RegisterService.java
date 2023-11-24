package com.soso.login.service.itf;

import com.soso.login.dto.RegisterMemberDTO;

public interface RegisterService {

    /**
     * 중복된 아이디가 있는지 확인한다.
     * @param loginId
     * @return
     */
    public boolean checkLoginIdDuplicated(String loginId);

    /**
     * 이메일 인증번호를 전송한다.
     * @param email
     * @return
     */
    public boolean sendCertifiedCodeToMail(String email);

    /**
     * 이메일 인증번호를 검증한다.
     * @param email
     * @return
     */
    public boolean checkMailFromCertifiedCode(String email, String certifiedCode);

    /**
     * 멤버를 등록한다.
     * @param registerMemberDTO
     * @return loginId
     */
    public String registerMember(RegisterMemberDTO registerMemberDTO) throws Exception;
}
