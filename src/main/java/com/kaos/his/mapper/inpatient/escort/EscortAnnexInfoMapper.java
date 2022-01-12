package com.kaos.his.mapper.inpatient.escort;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortAnnexInfo;

public interface EscortAnnexInfoMapper {
    /**
     * 主键查询
     * 
     * @param annexNo
     * @return
     */
    EscortAnnexInfo queryAnnexInfo(String annexNo);

    /**
     * 查询某个患者，某个时间段，审核/未审核 的记录
     * 
     * @param cardNo
     * @param beginDate
     * @param endDate
     * @param checked
     * @return
     */
    List<EscortAnnexInfo> queryAnnexInfos(String cardNo, Date beginDate, Date endDate, Boolean checked);

    /**
     * 插入一条附件记录
     * 
     * @param escortAnnexInfo
     * @return
     */
    int insertAnnexInfo(EscortAnnexInfo escortAnnexInfo);
}
