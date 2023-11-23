package com.soso.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CertifiedCodeDTO {
    String email;
    String certifiedCode;
    Timestamp sendDate;

}
