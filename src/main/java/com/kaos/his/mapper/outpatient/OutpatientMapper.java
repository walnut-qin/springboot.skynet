package com.kaos.his.mapper.outpatient;

import com.kaos.his.entity.outpatient.Outpatient;
import com.kaos.his.enums.common.TransTypeEnum;

public interface OutpatientMapper {
    /**
     * 主键查询
     * 
     * @param clinicCode 门诊号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param transType  交易类型；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    Outpatient queryOutpatient(String clinicCode, TransTypeEnum transType);
}
