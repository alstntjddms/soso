package com.soso.domain.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.JWT.JwtUtils;
import com.soso.domain.dto.DataDTO;
import com.soso.domain.dto.DatasDTO;
import com.soso.domain.dto.TeamDTO;
import com.soso.domain.repository.itf.DomainRAO;
import com.soso.domain.service.itf.DomainService;
import com.soso.login.repository.itf.LoginRAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        return rao.findTeamsByLoginId(loginRAO.findMemberByLoginId(loginId).getId());
    }

    @Override
    public TeamDTO createTeam(String sosoJwtToken, HashMap<String, String> reqData) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        reqData.put("memberId", String.valueOf(loginRAO.findMemberByLoginId(loginId).getId()));
        rao.createTeam(reqData);
        return null;
    }

    @Override
    public DatasDTO findDatasByLoginMember(String sosoJwtToken) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        List<DataDTO> a = rao.findDatasByLoginMember(loginRAO.findMemberByLoginId(loginId).getId());
        return processDatas(a);
    }


    public DatasDTO processDatas(List<DataDTO> dataDTOList) {
        Map<String, List<DataDTO>> allDataDTO = dataDTOList.stream()
                .collect(Collectors.groupingBy(DataDTO::getState));

        return new DatasDTO(
                allDataDTO.getOrDefault("요청", Collections.emptyList()),
                allDataDTO.getOrDefault("진행중", Collections.emptyList()),
                allDataDTO.getOrDefault("검토요청", Collections.emptyList()),
                allDataDTO.getOrDefault("결과", Collections.emptyList())
        );
    }

}
