package com.soso.domain.service;

import com.soso.common.utils.JWT.JwtUtils;
import com.soso.domain.dto.TeamDTO;
import com.soso.domain.dto.TeamMemberDTO;
import com.soso.domain.repository.itf.TeamRAO;
import com.soso.domain.service.itf.TeamService;
import com.soso.login.repository.itf.LoginRAO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRAO rao;

    @Autowired
    LoginRAO loginRAO;

    @Override
    public List<TeamDTO> findTeamsByLoginId(String sosoJwtToken) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        return rao.findTeamsByLoginId(loginRAO.findMemberByLoginId(loginId).getId());
    }

    @Override
    @Transactional
    public TeamDTO createTeam(String sosoJwtToken, HashMap<String, String> reqData) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        reqData.put("createMemberId", String.valueOf(loginRAO.findMemberByLoginId(loginId).getId()));
        reqData.put("id", "");
        rao.createTeam(reqData);

        rao.registerTeamMember(Integer.parseInt(reqData.get("id")), loginRAO.findMemberByLoginId(loginId).getId());
        return null;
    }

    @Override
    public void registerTeamMember(int teamId, int memberId) {
        rao.registerTeamMember(teamId, memberId);
    }

    @Override
    public TeamDTO loginTeam(String sosoJwtToken, TeamDTO teamDTO, HttpServletResponse response) {
        Claims claims = JwtUtils.getJwtTokenClaims(sosoJwtToken);
        String loginId = claims.get("loginId").toString();
        int memberId = loginRAO.findMemberByLoginId(loginId).getId();

        System.out.println("teamDTO = " + teamDTO);
        // JWT생성
        String jwt = JwtUtils.generateJwtToken(claims.get("email").toString(), claims.get("loginId").toString(), claims.get("name").toString(), String.valueOf(teamDTO.getId()));

        System.out.println("team = " + teamDTO);
        // response cookie jwt
        Cookie cookie = new Cookie("sosoJwtToken", jwt);
        cookie.setMaxAge(16 * 60 * 60); // 쿠키 만료 시간 (24시간)
        cookie.setPath("/"); // 쿠키의 유효 경로 설정 (전체 경로에 대해 유효)
        response.addCookie(cookie);

        return null;
    }

    @Override
    public List<TeamMemberDTO> findTeamMembersByLoginMember(String sosoJwtToken) {
        Claims claims = JwtUtils.getJwtTokenClaims(sosoJwtToken);
        String loginId = claims.get("loginId").toString();
        int memberId = loginRAO.findMemberByLoginId(loginId).getId();


        int teamId = Integer.parseInt(claims.get("teamId").toString());
        List<TeamMemberDTO> a = rao.findTeamMembersByLoginMember(teamId);
        return a;
    }

}
