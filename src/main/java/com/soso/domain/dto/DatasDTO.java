package com.soso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DatasDTO {

    List<DataDTO> 요청;
    List<DataDTO> 진행중;
    List<DataDTO> 검토요청;
    List<DataDTO> 결과;

}
