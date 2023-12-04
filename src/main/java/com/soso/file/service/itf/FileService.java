package com.soso.file.service.itf;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    public int uploadFile(MultipartFile file) throws IOException;

    public String downloadFile(String uuid, HttpServletResponse res) throws IOException;
}
