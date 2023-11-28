package com.soso.domain.service;

import com.soso.common.utils.JWT.JwtUtils;
import com.soso.domain.dto.*;
import com.soso.domain.repository.itf.DomainRAO;
import com.soso.domain.service.itf.DomainService;
import com.soso.login.repository.itf.LoginRAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ResponseDatasDTO findDatasByLoginMember(String sosoJwtToken) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        List<ResponseDataDTO> a = rao.findDatasByLoginMember(loginRAO.findMemberByLoginId(loginId).getId());
        return processDatas(a);
    }

    @Override
    @Transactional
    public int registerDataByFromMemberId(String sosoJwtToken, HashMap<String, String> reqData) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        int memberId = loginRAO.findMemberByLoginId(loginId).getId();

        String title = reqData.get("title");
        String content = reqData.get("content");
        int toMemberId = Integer.parseInt(reqData.get("toMemberId"));

        System.out.println("reqData = " + reqData);
        // 요청에 있는 모든 데이터 인덱스 +1
        rao.updateIndexByToMemberId(memberId);

        RegisterDataDTO registerDataDTO = new RegisterDataDTO();
        // 0번 인덱스로 넣기
        registerDataDTO.setDataIndex(0);
        registerDataDTO.setFromMemberId(memberId);
        registerDataDTO.setToMemberId(toMemberId);
        registerDataDTO.setTeamId(1);
        registerDataDTO.setTitle(title);
        registerDataDTO.setContent(content);

        return rao.registerDataByToMemberId(registerDataDTO);

    }

    @Override
    public int updateDataByFromMemberId(String sosoJwtToken, ResponseDatasDTO responseDatasDTO) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        int memberId = loginRAO.findMemberByLoginId(loginId).getId();

        List<ResponseDataDTO> mergeList = new ArrayList<>();
        mergeList.addAll(responseDatasDTO.get요청());
        mergeList.addAll(responseDatasDTO.get진행중());
        mergeList.addAll(responseDatasDTO.get검토요청());
        mergeList.addAll(responseDatasDTO.get결과());
        return rao.updateDataByFromMemberId(mergeList);
    }


    public ResponseDatasDTO processDatas(List<ResponseDataDTO> dataDTOList) {
        Map<String, List<ResponseDataDTO>> allDataDTO = dataDTOList.stream()
                .collect(Collectors.groupingBy(ResponseDataDTO::getState));

        return new ResponseDatasDTO(
                allDataDTO.getOrDefault("요청", Collections.emptyList()),
                allDataDTO.getOrDefault("진행중", Collections.emptyList()),
                allDataDTO.getOrDefault("검토요청", Collections.emptyList()),
                allDataDTO.getOrDefault("결과", Collections.emptyList())
        );
    }

}
