package com.kaos.his.service.inf.common;

import com.kaos.his.entity.common.ComPatientInfo;

public interface PatientInfoService {
    /**
     * 查询患者实体
     * 
     * @param cardNo 就诊卡号
     * @return
     */
    ComPatientInfo queryPatientInfo(String cardNo);
}
