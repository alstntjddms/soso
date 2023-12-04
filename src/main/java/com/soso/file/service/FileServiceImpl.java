package com.soso.file.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.file.dto.FileDTO;
import com.soso.file.repository.itf.FileRAO;
import com.soso.file.service.itf.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.location}")
    private String uploadPath;

    @Autowired
    FileRAO rao;

    @Override
    public int uploadFile(MultipartFile file) throws IOException {

        if(file == null || file.isEmpty()){
            throw new CustomException(ExceptionStatus.EMPTY_FILE);
        }

        // 파일이름이 빈값인지 체크
        if(Objects.requireNonNull(file.getOriginalFilename()).isEmpty()){
            throw new CustomException(ExceptionStatus.EMPTY_FILE_NAME);
        }

        // 100MB 넘는지 체크
        if(file.getSize()> 104857600){
            throw new CustomException(ExceptionStatus.OVER_100MB_FILE);
        }

        String originalName = file.getOriginalFilename();
        assert originalName != null;
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid;
        Path savePath = Paths.get(saveName);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setUuid(uuid);
        fileDTO.setFileName(fileName);
        fileDTO.setFilePath(uploadPath + File.separator + folderPath);

        try {
            file.transferTo(savePath);
            rao.uploadFile(fileDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public String downloadFile(String uuid, HttpServletResponse res) throws IOException {
        FileDTO fileDTO = rao.findFileByUuid(uuid);

        if (fileDTO == null) {
            throw new CustomException(ExceptionStatus.FILE_NOT_FOUND);
        }

        String filePath = fileDTO.getFilePath() + File.separator + fileDTO.getUuid();
        Path file = Paths.get(filePath);

        if (Files.exists(file)) {
            // Set content type
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileDTO.getFileName(),"UTF-8")+"\";");
            res.setHeader("Content-Transfer-Encoding", "binary");
            Files.copy(file, res.getOutputStream());
            res.flushBuffer();
        } else {
            throw new CustomException(ExceptionStatus.FILE_NOT_FOUND);
        }
        return null;
    }


    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM월dd일"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);

        if(!uploadPathFolder.exists()) {
            boolean mkdirs = uploadPathFolder.mkdirs();
//            log.info("-------------------makeFolder------------------");
//            log.info("uploadPathFolder.exists(): "+uploadPathFolder.exists());
//            log.info("mkdirs: "+mkdirs);
        }

        return folderPath;

    }


}
