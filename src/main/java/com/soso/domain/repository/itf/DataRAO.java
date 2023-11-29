package com.soso.domain.repository.itf;

import com.soso.domain.dto.DataDTO;
import com.soso.domain.dto.RegisterDataDTO;
import com.soso.domain.dto.ResponseDataDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface DataRAO {

    public void createTeam(HashMap<String, String> teamName);

    public List<ResponseDataDTO> findDatasByLoginMember(@Param("memberId") String memberId, @Param("teamId") String teamId);

    public DataDTO findDataByDataId(int id);

    public int registerDataByToMemberId(RegisterDataDTO registerDataDTO);

    public int updateDataByFromMemberId(DataDTO dataDTO);

    public void updateIndexByToMemberId(int memberId);

    public int updateDatasByFromMemberId(List datasDTO);
}
