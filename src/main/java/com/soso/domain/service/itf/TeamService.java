package com.soso.domain.service.itf;

import com.soso.domain.dto.TeamDTO;
import com.soso.domain.dto.TeamMemberDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;

public interface TeamService {

    /**
     * 로그인한 멤버가 가입한 팀을 조회한다.
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

    public void registerTeamMember(int teamId, int loginId);

    /**
     * 팀에 로그인한다.
     * @param sosoJwtToken
     * @return
     */
    public TeamDTO loginTeam(String sosoJwtToken, TeamDTO teamDTO, HttpServletResponse response);


    /**
     * 로그인한 멤버로 팀 멤버를 조회한다.
     * @param sosoJwtToken
     */
    public List<TeamMemberDTO> findTeamMembersByLoginMember(String sosoJwtToken);
}
