package com.soso.domain.service;

import com.soso.common.utils.JWT.JwtUtils;
import com.soso.domain.dto.TeamDTO;
import com.soso.domain.repository.itf.DomainRAO;
import com.soso.domain.service.itf.DomainService;
import com.soso.login.repository.itf.LoginRAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    DomainRAO rao;

    @Autowired
    LoginRAO loginRAO;

    @Override
    public String domain() {
        return "domain";
    }

    @Override
    public List<TeamDTO> findTeamsByLoginId(String sosoJwtToken) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        return rao.findTeamsByLoginId(String.valueOf(loginRAO.findMemberByLoginId(loginId).getId()));
    }

    @Override
    public TeamDTO createTeam(String sosoJwtToken, HashMap<String, String> reqData) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        reqData.put("memberId", String.valueOf(loginRAO.findMemberByLoginId(loginId).getId()));
        rao.createTeam(reqData);
        return null;
    }
}
