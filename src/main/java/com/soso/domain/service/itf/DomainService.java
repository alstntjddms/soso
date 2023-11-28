package com.soso.domain.service.itf;

import com.soso.domain.dto.DataDTO;
import com.soso.domain.dto.DatasDTO;
import com.soso.domain.dto.ResponseDatasDTO;
import com.soso.domain.dto.TeamDTO;

import java.util.HashMap;
import java.util.List;

public interface DomainService {

    public String domain();

    /**
     * 로그인한 멤버정보로 팀을 조회한다.
     * @param sosoJwtToken
     * @return
     */
    public List<TeamDTO> findTeamsByLoginId(String sosoJwtToken);

    /**
     * 로그인한 멤버정보로 팀을 생성한다.
     * @param sosoJwtToken
     * @return
     */
    public TeamDTO createTeam(String sosoJwtToken, HashMap<String, String> reqData);

    public ResponseDatasDTO findDatasByLoginMember(String sosoJwtToken);

    public DataDTO findDataByDataId(String sosoJwtToken, int id);
    public int registerDataByFromMemberId(String sosoJwtToken, HashMap<String, String> reqData);

    public int updateDataByFromMemberId(String sosoJwtToken, ResponseDatasDTO responseDatasDTO);
}
