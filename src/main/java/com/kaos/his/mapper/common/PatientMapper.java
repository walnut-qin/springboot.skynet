package com.kaos.his.mapper.common;

import com.kaos.his.entity.common.Patient;

public interface PatientMapper {
    /**
     * 主键查询
     * 
     * @param cardNo 就诊卡号；值为 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    Patient queryPatient(String cardNo);
}
