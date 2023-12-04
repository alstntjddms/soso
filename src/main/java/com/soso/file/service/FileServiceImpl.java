package com.soso.file.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.file.dto.FileDTO;
import com.soso.file.repository.itf.FileRAO;
import com.soso.file.service.itf.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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


        // 날짜 폴더 생성
        String folderPath = makeFolder();

        // UUID
        String uuid = UUID.randomUUID().toString();

        // 저장할 파일 이름 중간에 "_"를 이용해서 구현
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid;

        Path savePath = Paths.get(saveName);

        try {
            file.transferTo(savePath); // 실제 이미지 저장
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }


    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder --------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(!uploadPathFolder.exists()) {
            boolean mkdirs = uploadPathFolder.mkdirs();
//            log.info("-------------------makeFolder------------------");
//            log.info("uploadPathFolder.exists(): "+uploadPathFolder.exists());
//            log.info("mkdirs: "+mkdirs);
        }

        return folderPath;

    }
    @Override
    public void downloadFile() {

    }


}
