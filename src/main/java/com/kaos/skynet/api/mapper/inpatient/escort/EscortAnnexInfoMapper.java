package com.kaos.skynet.api.mapper.inpatient.escort;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.entity.inpatient.escort.EscortAnnexInfo;

public interface EscortAnnexInfoMapper {
    /**
     * 查询上传的附件实体
     * 
     * @param annexNo 附件号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortAnnexInfo queryAnnexInfo(String annexNo);

    /**
     * 查询符合条件的附件记录
     * 
     * @param helperCardNo 陪护人卡号；等于 {@code null} 时，不作为判断条件
     * @param beginDate    开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate      结束时间；等于 {@code null} 时，不作为判断条件
     * @param checked      审核标识；等于 {@code null} 时，不作为判断条件
     * @return 符合条件的附件记录（按照上传时间排序）
     */
    List<EscortAnnexInfo> queryAnnexInfos(String helperCardNo, LocalDateTime beginDate, LocalDateTime endDate,
            Boolean checked);

    /**
     * 插入一条附件记录
     * 
     * @param escortAnnexInfo 附件实体；不必设置 {@code annexNo}，执行语句时自动生成
     * @return
     */
    int insertAnnexInfo(EscortAnnexInfo escortAnnexInfo);
}
