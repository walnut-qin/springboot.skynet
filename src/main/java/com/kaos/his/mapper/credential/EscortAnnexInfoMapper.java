package com.kaos.his.mapper.credential;

import java.util.List;

import com.kaos.his.entity.credential.EscortAnnexInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortAnnexInfoMapper {
    /**
     * 主键查询
     * 
     * @param annexNo
     * @return
     */
    EscortAnnexInfo QueryEscortAnnexInfo(String annexNo);

    /**
     * 获取指定患者的某个时间段内上传的还未审核的附件
     * 
     * @param cardNo
     * @return
     */
    List<EscortAnnexInfo> QueryUncheckedEscortAnnexInfos(String cardNo);

    /**
     * 查询某个科室下所有未审核的结果
     * 
     * @param deptCode
     * @return
     */
    List<EscortAnnexInfo> QueryAllUncheckedEscortAnnexInfos();

    /**
     * 插入新纪录
     * 
     * @param escortAnnex
     */
    int InsertEscortAnnexInfo(EscortAnnexInfo aEscortAnnexInfo);
}
