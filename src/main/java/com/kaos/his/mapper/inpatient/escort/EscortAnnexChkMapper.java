package com.kaos.his.mapper.inpatient.escort;

import com.kaos.his.entity.inpatient.escort.EscortAnnexChk;

public interface EscortAnnexChkMapper {
    /**
     * 查询审核结果
     * 
     * @param annexNo
     * @return
     */
    EscortAnnexChk queryAnnexChk(String annexNo);

    /**
     * 插入审核记录
     */
    int insertAnnexChk(EscortAnnexChk escortAnnexChk);
}
