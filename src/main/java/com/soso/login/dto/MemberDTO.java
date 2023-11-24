package com.soso.login.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberDTO {
    int id;
    String loginId;
    String password;
    String tempPassword;
    String salt;
    String email;
    String name;
    Timestamp regDate;
    Timestamp updDate;
}
