package com.soso.domain.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TeamDTO {
    int id;
    String memberId;
    String TeamName;
    int teamType;
    Timestamp regDate;
    Timestamp updDate;
}
