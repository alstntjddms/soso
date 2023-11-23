package com.soso.login.dto;

import lombok.Data;

@Data
public class RegisterMemberDTO {
    String loginId;
    String password;
    String name;
    String email;
}
