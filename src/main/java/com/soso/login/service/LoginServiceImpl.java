package com.soso.login.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.JWT.JwtUtils;
import com.soso.common.utils.hash.SHA256;
import com.soso.login.dto.LoginMemberDTO;
import com.soso.login.dto.MemberDTO;
import com.soso.login.repository.itf.LoginRAO;
import com.soso.login.service.itf.LoginService;
import io.jsonwebtoken.Claims;
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
    public MemberDTO loginMember(LoginMemberDTO loginMemberDTO, HttpServletResponse response) throws Exception {
        // 아이디, 비밀번호 유효성 검사 ex) 아이디 6자이상 영어, 비밀번호 8자 이상
        // loginId 6자 이하 OR loginId 30자 이상
        if(loginMemberDTO.getLoginId().length() < 6 || loginMemberDTO.getLoginId().length() > 30){
            throw new CustomException(ExceptionStatus.LOGIN_ID_LENGTH);
        }
        // password 8자 이하
        if(loginMemberDTO.getPassword().length() < 8 ){
            throw new CustomException(ExceptionStatus.PASSWORD_LENGTH);
        }

        // DB에서 아이디에 해당되는 Member가져옴
        MemberDTO memberDTO = rao.findMemberByLoginId(loginMemberDTO.getLoginId());

        // Member null 체크
        if (memberDTO == null) {
            throw new CustomException(ExceptionStatus.LOGIN_ID_WRONG);
        }

        // dbSalt와 사용자입력 password를 hash후 db에 저장된 hash된 password와 같은지 비교
        if(!sha256.hash(loginMemberDTO.getPassword(), memberDTO.getSalt()).equals(memberDTO.getPassword())){
            throw new CustomException(ExceptionStatus.PASSWORD_WRONG);
        }

        // JWT생성
        String jwt = JwtUtils.generateJwtToken(memberDTO.getEmail(), memberDTO.getLoginId(), memberDTO.getName(), null);

        // response cookie jwt
        Cookie cookie = new Cookie("sosoJwtToken", jwt);
        cookie.setMaxAge(16 * 60 * 60); // 쿠키 만료 시간 (24시간)
        cookie.setPath("/"); // 쿠키의 유효 경로 설정 (전체 경로에 대해 유효)
        response.addCookie(cookie);

        return new MemberDTO();
    }

    @Override
    public boolean findPassword(String email, String loginId) {
        return false;
    }

    @Override
    public HashMap<String, String> findLoginMember(String sosoJwtToken) {
        Claims claims = JwtUtils.getJwtTokenClaims(sosoJwtToken);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name", claims.get("name").toString());
        hashMap.put("email", claims.get("email").toString());
        return hashMap;
    }

}
