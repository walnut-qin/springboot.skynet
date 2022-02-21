package com.kaos.his.mapper.inpatient;

import com.kaos.his.entity.inpatient.FinIprInMainInfo;

public interface FinIprInMainInfoMapper {
    /**
     * 查询住院主表记录
     * 
     * @param inpatientNo 住院流水号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIprInMainInfo queryInMainInfo(String inpatientNo);
}
