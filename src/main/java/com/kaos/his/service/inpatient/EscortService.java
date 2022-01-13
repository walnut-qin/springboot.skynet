package com.kaos.his.service.inpatient;

import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortMainInfo;

public interface EscortService {
    /**
     * 查询陪护证，主键查询
     * 
     * @param escortNo
     * @return
     */
    EscortMainInfo queryEscortStateInfo(String escortNo);

    /**
     * 查询被陪护的患者
     * 
     * @param patientCardNo
     * @return
     */
    List<EscortMainInfo> queryPatientInfos(String helperCardNo);
}
