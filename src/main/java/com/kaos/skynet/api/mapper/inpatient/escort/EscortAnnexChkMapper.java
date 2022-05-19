package com.kaos.skynet.api.mapper.inpatient.escort;

import com.kaos.skynet.api.entity.inpatient.escort.EscortAnnexChk;

public interface EscortAnnexChkMapper {
    /**
     * 查询附件审核记录
     * 
     * @param annexNo 附件号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortAnnexChk queryAnnexChk(String annexNo);

    /**
     * 添加一条附件审核记录
     * 
     * @param escortAnnexChk 审核记录实体
     * @return
     */
    int insertAnnexChk(EscortAnnexChk escortAnnexChk);
}
