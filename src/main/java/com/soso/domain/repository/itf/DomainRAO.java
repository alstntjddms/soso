package com.soso.domain.repository.itf;

import com.soso.domain.dto.TeamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface DomainRAO {
    public String domain();

    public List<TeamDTO> findTeamsByLoginId(String loginId);
    
    public void createTeam(HashMap<String, String> teamName);

}
