package com.soso.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseDataDTO {
    int id;
    int dataIndex;
    String state;
    String title;
    Timestamp regDate;
    Timestamp updDate;
}
