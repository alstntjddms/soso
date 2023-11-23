package com.soso.login.repository.itf;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface RegisterRAO {
        public List<HashMap> test();

}
