package com.kaos.his.service.common;

import com.kaos.his.entity.common.Patient;

public interface EntityInfoService {
    /**
     * 查询患者实体
     * 
     * @param cardNo 就诊卡号
     * @return
     */
    Patient queryPatient(String cardNo);
}
