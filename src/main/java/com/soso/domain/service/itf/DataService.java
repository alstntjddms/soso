package com.soso.domain.service.itf;

import com.soso.domain.dto.DataDTO;
import com.soso.domain.dto.ResponseDatasDTO;

import java.util.HashMap;

public interface DataService {

    /**
     * 로그인멤버의 데이터를 조회한다.
     * @param sosoJwtToken
     * @return
     */
    public ResponseDatasDTO findDatasByLoginMember(String sosoJwtToken);

    /**
     * 데이터ID로 데이터를 조회한다.
     * @param sosoJwtToken
     * @return
     */
    public DataDTO findDataByDataId(String sosoJwtToken, int id);

    /**
     * 데이터를 저장한다.
     * @param sosoJwtToken
     * @param reqData
     * @return
     */
    public int registerDataByFromMemberId(String sosoJwtToken, HashMap<String, String> reqData);

    /**
     * 데이터를 업데이트한다.
     */
    public int updateDataByFromMemberId(String sosoJwtToken, DataDTO dataDTO);

    /**
     * state 또는 dataIndex가 바뀌었으면 사용자의 데이터를 업데이트 한다.
     * @param sosoJwtToken
     * @param responseDatasDTO
     * @return
     */
    public int updateDatasByFromMemberId(String sosoJwtToken, ResponseDatasDTO responseDatasDTO);
}
