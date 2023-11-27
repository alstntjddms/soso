package com.soso.login.repository.itf;

import com.soso.login.dto.MemberDTO;
import com.soso.login.dto.RegisterMemberDTO;
import com.soso.domain.dto.TeamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface LoginRAO {

        public List<HashMap> test();

        /**
         * memberId로 멤버 정보를 조회한다.
         * @param memberId
         * @return
         */
        public MemberDTO findMemberByLoginId(String memberId);


}
