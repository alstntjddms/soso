package com.soso.login.dto;

import lombok.Data;
import java.util.ArrayList;

@Data
public class MemberDTO {
    String loginId;
    String name;
    String email;
    ArrayList<String> teams;
}
