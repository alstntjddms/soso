package com.soso.domain.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TeamDTO {
    int id;
    int createMemberId;
    String teamName;
    int teamType;
    Timestamp regDate;
    Timestamp updDate;
}
