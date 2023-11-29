package com.soso.domain.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.JWT.JwtUtils;
import com.soso.domain.dto.DataDTO;
import com.soso.domain.dto.RegisterDataDTO;
import com.soso.domain.dto.ResponseDataDTO;
import com.soso.domain.dto.ResponseDatasDTO;
import com.soso.domain.repository.itf.DataRAO;
import com.soso.domain.service.itf.DataService;
import com.soso.login.repository.itf.LoginRAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    DataRAO rao;

    @Autowired
    LoginRAO loginRAO;

    @Override
    public ResponseDatasDTO findDatasByLoginMember(String sosoJwtToken) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        String teamId =  JwtUtils.getJwtTokenClaims(sosoJwtToken).get("teamId").toString();
        if(teamId.isBlank()){
            throw new CustomException(ExceptionStatus.INVALID_LOGIN);
        }
        List<ResponseDataDTO> a = rao.findDatasByLoginMember(String.valueOf(loginRAO.findMemberByLoginId(loginId).getId()), teamId);
        return processDatas(a);
    }

    @Override
    public DataDTO findDataByDataId(String sosoJwtToken, int id) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        int memberId = loginRAO.findMemberByLoginId(loginId).getId();
        DataDTO dataDTO = rao.findDataByDataId(id);
        if(dataDTO.getToMemberId() != memberId){
            throw new CustomException(ExceptionStatus.NO_INQUIRY_PERMISSION);
        }
        return dataDTO;
    }

    @Override
    @Transactional
    public int registerDataByFromMemberId(String sosoJwtToken, HashMap<String, String> reqData) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        int teamId = Integer.parseInt(JwtUtils.getJwtTokenClaims(sosoJwtToken).get("teamId").toString());

        int memberId = loginRAO.findMemberByLoginId(loginId).getId();

        String title = reqData.get("title");
        String content = reqData.get("content");
        int toMemberId = Integer.parseInt(reqData.get("toMemberId"));

        System.out.println("reqData = " + reqData);
        // 요청에 있는 모든 데이터 인덱스 +1
        rao.updateIndexByToMemberId(toMemberId);

        RegisterDataDTO registerDataDTO = new RegisterDataDTO();
        // 0번 인덱스로 넣기
        registerDataDTO.setDataIndex(0);
        registerDataDTO.setFromMemberId(memberId);
        registerDataDTO.setToMemberId(toMemberId);
        registerDataDTO.setTeamId(teamId);
        registerDataDTO.setTitle(title);
        registerDataDTO.setContent(content);

        return rao.registerDataByToMemberId(registerDataDTO);

    }

    @Override
    public int updateDataByFromMemberId(String sosoJwtToken, DataDTO dataDTO) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        System.out.println("dataDTO = " + dataDTO);
        return rao.updateDataByFromMemberId(dataDTO);
    }

    @Override
    public int updateDatasByFromMemberId(String sosoJwtToken, ResponseDatasDTO responseDatasDTO) {
        String loginId = JwtUtils.getJwtTokenClaims(sosoJwtToken).get("loginId").toString();
        int memberId = loginRAO.findMemberByLoginId(loginId).getId();

        List<ResponseDataDTO> mergeList = new ArrayList<>();
        mergeList.addAll(responseDatasDTO.get요청());
        mergeList.addAll(responseDatasDTO.get진행중());
        mergeList.addAll(responseDatasDTO.get검토요청());
        mergeList.addAll(responseDatasDTO.get결과());
        return rao.updateDatasByFromMemberId(mergeList);
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
