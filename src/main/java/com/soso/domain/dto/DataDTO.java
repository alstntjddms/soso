package com.soso.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DataDTO {
    int seq;
    int index;
    String state;
    String fromMemberId;
    String toMemberId;
    String teamID;
    String title;
    String content;
    boolean delYn;
    Timestamp regDate;
    Timestamp updDate;
}