package com.kaos.his.mapper.inpatient;

import java.util.List;

import com.kaos.his.entity.inpatient.FinIprPrepayIn;
import com.kaos.his.enums.FinIprPrepayInStateEnum;

public interface FinIprPrepayInMapper {
    /**
     * 主键查询
     * 
     * @param cardNo
     * @param happenNo
     * @return
     */
    FinIprPrepayIn queryPrepayIn(String cardNo, Integer happenNo);

    /**
     * 查询患者所有陪护证
     * 
     * @param cardNo
     * @return
     */
    List<FinIprPrepayIn> queryPrepayIns(String cardNo, List<FinIprPrepayInStateEnum> states);

    /**
     * 获取最后一张住院证
     * 
     * @param cardNo
     * @return
     */
    FinIprPrepayIn queryLastPrepayIn(String cardNo);
}
