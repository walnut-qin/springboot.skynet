package com.kaos.his.mapper.common;

import com.kaos.his.entity.common.Patient;

public interface PatientMapper {
    /**
     * 主键查询
     * @param cardNo
     * @param valid
     * @return
     */
    Patient queryPatient(String cardNo);
}
