package com.soso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDatasDTO {

    List<ResponseDataDTO> 요청;
    List<ResponseDataDTO> 진행중;
    List<ResponseDataDTO> 검토요청;
    List<ResponseDataDTO> 결과;

}
