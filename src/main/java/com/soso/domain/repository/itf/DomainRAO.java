package com.soso.domain.repository.itf;

import com.soso.domain.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface DomainRAO {
    public String domain();

}
