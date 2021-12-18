package com.kaos.his.mapper.credential;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.credential.AnnexInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface AnnexInfoMapper {
    /**
     * 主键查询
     * 
     * @param annexNo
     * @return
     */
    List<AnnexInfo> QueryAnnexInfo(String annexNo);

    /**
     * 获取某个时间段内上传的指定患者的附件
     * 
     * @param cardNo
     * @param beginDate
     * @param endDate
     * @return
     */
    List<AnnexInfo> QueryAnnexInfos(String cardNo, Date beginDate, Date endDate);

    /**
     * 插入新纪录
     * 
     * @param escortAnnex
     */
    void InsertAnnexInfo(AnnexInfo annexInfo);
}
