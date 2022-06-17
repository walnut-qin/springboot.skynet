package com.kaos.skynet.api.data.his.mapper.outpatient;

import com.kaos.skynet.api.data.his.entity.outpatient.FinOprRegister;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;

public interface FinOprRegisterMapper {
    /**
     * 主键查询
     * 
     * @param clinicCode 门诊号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param transType  交易类型, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinOprRegister queryRegister(String clinicCode, TransTypeEnum transType);
}
