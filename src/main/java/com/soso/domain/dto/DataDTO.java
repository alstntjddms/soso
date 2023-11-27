package com.soso.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DataDTO {
    int id;
    int index;
    String state;
    String fromMemberId;
    String toMemberId;
    String teamId;
    String title;
    String content;
    boolean delYn;
    Timestamp regDate;
    Timestamp updDate;
}