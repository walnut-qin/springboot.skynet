package com.kaos.his.mapper;

import com.kaos.his.entity.personnel.Patient;

public interface PatientMapper {
    /**
     * 根据就诊卡号获取患者实体
     * 
     * @param cardNo 就诊卡号
     * @return 患者实体
     */
    Patient GetPatientByCardNo(String cardNo);
}
