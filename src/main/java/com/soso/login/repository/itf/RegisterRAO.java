package com.soso.login.repository.itf;

import com.soso.login.dto.RegisterMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface RegisterRAO {
        public List<HashMap> test();
        public String checkLoginIdDuplicated(String loginId);
        public String checkEmailDuplicated(String email);

        public void registerMember(RegisterMemberDTO registerMember);

}
