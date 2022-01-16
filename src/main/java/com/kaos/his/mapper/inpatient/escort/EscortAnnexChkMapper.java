package com.kaos.his.mapper.inpatient.escort;

import java.util.Date;
import java.util.List;

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
     * 查询某个时段内的核酸结果
     * 
     * @param cardNo
     * @param beginDate 核酸检测时间边界（非审核时间）
     * @param endDate   核酸检测时间边界（非审核时间）
     * @return
     */
    List<EscortAnnexChk> queryAnnexChks(String cardNo, Date beginDate, Date endDate);

    /**
     * 插入审核记录
     */
    int insertAnnexChk(EscortAnnexChk escortAnnexChk);
}
