package com.soso.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FileDTO {
    private int id;
    private String uuid;
    private String fileName;
    private String filePath;
    private boolean tempFile;
    private boolean delYn;
    private Timestamp regDate;

}
