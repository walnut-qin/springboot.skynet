package com.kaos.his.mapper.inpatient.escort;

import com.kaos.his.entity.inpatient.escort.EscortVip;

public interface EscortVipMapper {
    /**
     * 检索VIP
     */
    EscortVip queryEscortVip(String patientCardNo, Integer happenNo);

    /**
     * 插入一条新纪录
     */
    int insertEscortVip(EscortVip escortVip);
}
