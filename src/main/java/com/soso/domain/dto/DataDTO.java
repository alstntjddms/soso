package com.soso.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DataDTO {
    int id;
    int index;
    String state;
    int fromMemberId;
    int toMemberId;
    int teamId;
    String title;
    String content;
    boolean delYn;
    Timestamp regDate;
    Timestamp updDate;
}