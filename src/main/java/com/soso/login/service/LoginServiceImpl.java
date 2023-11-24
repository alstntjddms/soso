package com.soso.login.service;

import com.soso.common.utils.JWT.JwtUtils;
import com.soso.common.utils.hash.SHA256;
import com.soso.login.dto.LoginMemberDTO;
import com.soso.login.dto.MemberDTO;
import com.soso.login.repository.itf.LoginRAO;
import com.soso.login.service.itf.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginRAO rao;

    @Autowired
    SHA256 sha256;

    @Override
    public List<HashMap> test() {
        return rao.test();
    }

    @Override
    public MemberDTO loginMember(LoginMemberDTO loginMemberDTO, HttpServletResponse response) {
//        System.out.println("loginMemberDTO = " + loginMemberDTO);
//        // 아이디, 비밀번호 유효성 검사 ex) 아이디 6자이상 영어, 비밀번호 8자 이상
//
//        // DB에서 아이디에 해당되는 hash된 비밀번호와 salt가져옴
//        String dbPassword = "qwer";
//        String dbSalt = "1234";
//
//        // dbSalt와 사용자입력 password를 hash후 db에 저장된 hash된 password와 같은지 비교
//        if(!sha256.hash(loginMemberDTO.getPassword(), dbSalt).equals(dbPassword)){
//            throw new Exception();
//        }
//
//        // 멤버정보 DB에서 조회
//        MemberDTO memberDTO = new MemberDTO();
//
//        // JWT생성
//        String jwt = JwtUtils.generateJwtToken(memberDTO.getEmail(), memberDTO.getName());
        String jwt = JwtUtils.generateJwtToken("minsu@naver.com", "전민수");

        // response cookie jwt
        Cookie cookie = new Cookie("sosoJwtToken", jwt);
        cookie.setMaxAge(24 * 60 * 60); // 쿠키 만료 시간 (24시간)
        cookie.setPath("/"); // 쿠키의 유효 경로 설정 (전체 경로에 대해 유효)
        response.addCookie(cookie);

        return new MemberDTO();
    }

    @Override
    public boolean findPassword(String email, String loginId) {
        return false;
    }
}
