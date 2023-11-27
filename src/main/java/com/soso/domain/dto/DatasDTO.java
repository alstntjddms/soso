package com.soso.domain.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;

@Data
public class DatasDTO {

    HashMap<String, ArrayList<DataDTO>> datas;

}
