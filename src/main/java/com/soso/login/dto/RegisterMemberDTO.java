package com.soso.login.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
public class RegisterMemberDTO {
    String loginId;
    String password;
    String salt;
    String name;
    String email;
    String certifiedCode;
}
