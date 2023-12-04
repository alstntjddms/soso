package com.soso.file.service.itf;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    public int uploadFile(MultipartFile file) throws IOException;

    public void downloadFile();
}
