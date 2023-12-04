package com.soso.file.repository.itf;

import com.soso.file.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
@Mapper
public interface FileRAO {

    public int uploadFile(FileDTO fileDTO);

    public FileDTO findFileByUuid(String uuid);
}
