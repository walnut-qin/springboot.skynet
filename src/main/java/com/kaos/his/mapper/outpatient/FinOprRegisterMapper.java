package com.kaos.his.mapper.outpatient;

import com.kaos.his.entity.outpatient.FinOprRegister;
import com.kaos.his.enums.common.TransTypeEnum;

public interface FinOprRegisterMapper {
    /**
     * 主键查询
     * 
     * @param clinicCode 门诊号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param transType  交易类型, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinOprRegister queryRegisterRec(String clinicCode, TransTypeEnum transType);

    /**
     * 更新看诊标识
     * 
     * @param clinicCode 门诊号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param transType  交易类型, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param seeFlag    看诊标识
     * @return
     */
    int updateSeeFlag(String clinicCode, TransTypeEnum transType, Boolean seeFlag);

    /**
     * 插入记录
     * 
     * @param register 挂号记录
     * @return
     */
    int insertRegisterRec(FinOprRegister register);
}
