package com.soso.file.service.itf;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

public interface FileService {

    public HashMap<String, String> uploadFile(MultipartFile file) throws IOException;

    public String downloadFile(String uuid, HttpServletResponse res) throws IOException;
}
