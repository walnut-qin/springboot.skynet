package com.kaos.skynet.api.mapper.inpatient;

import com.kaos.skynet.entity.inpatient.FinSpecialCityPatient;

public interface FinSpecialCityPatientMapper {
    /**
     * 查询患者特殊标识
     * 
     * @param inpatientNo 住院流水号
     * @return
     */
    FinSpecialCityPatient querySpecialCityPatient(String inpatientNo);
}
