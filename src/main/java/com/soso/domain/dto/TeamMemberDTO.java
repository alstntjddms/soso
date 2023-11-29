package com.soso.domain.dto;

import lombok.Data;

@Data
public class TeamMemberDTO {
    int memberId;
    int teamId;
    String memberName;
    String teamName;
    String teamType;
}
