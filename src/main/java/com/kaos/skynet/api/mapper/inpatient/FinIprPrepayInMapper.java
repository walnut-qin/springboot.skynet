package com.kaos.skynet.api.mapper.inpatient;

import java.util.List;

import com.kaos.skynet.api.entity.inpatient.FinIprPrepayIn;
import com.kaos.skynet.enums.inpatient.FinIprPrepayInStateEnum;

public interface FinIprPrepayInMapper {
    /**
     * 主键查询
     * 
     * @param cardNo   就诊卡号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param happenNo 住院证序号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIprPrepayIn queryPrepayIn(String cardNo, Integer happenNo);

    /**
     * 查询患者所有陪护证
     * 
     * @param cardNo 就诊卡号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param states 状态清单；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinIprPrepayIn> queryPrepayIns(String cardNo, List<FinIprPrepayInStateEnum> states);

    /**
     * 获取最后一张住院证
     * 
     * @param cardNo
     * @param states 状态清单；等于 {@code null} 时，不作为判断条件
     * @return
     */
    FinIprPrepayIn queryLastPrepayIn(String cardNo, List<FinIprPrepayInStateEnum> states);
}
