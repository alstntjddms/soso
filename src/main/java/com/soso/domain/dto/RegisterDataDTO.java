package com.soso.domain.dto;

import lombok.Data;

@Data
public class RegisterDataDTO {
    int dataIndex;
    int fromMemberId;
    int toMemberId;
    int teamId;
    String title;
    String content;
}
