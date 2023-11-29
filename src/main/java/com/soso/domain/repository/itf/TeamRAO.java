package com.soso.domain.repository.itf;

import com.soso.domain.dto.TeamDTO;
import com.soso.domain.dto.TeamMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface TeamRAO {

    public List<TeamDTO> findTeamsByLoginId(int memberId);

    public int createTeam(HashMap<String, String> teamName);

    public void registerTeamMember(@Param("teamId") int teamId, @Param("memberId") int memberId);

    public List<TeamMemberDTO> findTeamMembersByLoginMember(int teamId);
}
