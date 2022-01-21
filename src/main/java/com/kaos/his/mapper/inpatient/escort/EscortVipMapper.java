package com.kaos.his.mapper.inpatient.escort;

import com.kaos.his.entity.inpatient.escort.EscortVip;

public interface EscortVipMapper {
    /**
     * 主键查询
     * 
     * @param patientCardNo 患者卡号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param happenNo      住院证序号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortVip queryEscortVip(String patientCardNo, Integer happenNo);

    /**
     * 插入VIP记录
     * 
     * @param escortVip VIP记录
     * @return
     */
    int insertEscortVip(EscortVip escortVip);
}
