package com.kaos.his.mapper.outpatient;

import com.kaos.his.entity.outpatient.Outpatient;
import com.kaos.his.enums.TransTypeEnum;

public interface OutpatientMapper {
    /**
     * 主键查询
     * @param clinicCode
     * @param transType
     * @return
     */
    Outpatient queryOutpatient(String clinicCode, TransTypeEnum transType);
}
