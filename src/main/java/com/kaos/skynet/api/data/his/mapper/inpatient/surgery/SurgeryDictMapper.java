package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict;

public interface SurgeryDictMapper {
    /**
     * 主键查询
     * 
     * @param icdCode ICD-9手术编码
     * @return
     */
    SurgeryDict querySurgeryDict(String icdCode);
}
