package com.kaos.his.mapper.credential;

import com.kaos.his.entity.credential.EscortAnnexCheck;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortAnnexCheckMapper {
    /**
     * 主键查询
     * 
     * @param annexNo
     * @return
     */
    EscortAnnexCheck QueryEscortAnnexCheck(String annexNo);

    /**
     * 查询特定人员最近一次核酸检测结果（最近生效而非最近审核）
     * 
     * @param cardNo
     * @return
     */
    EscortAnnexCheck QueryLastExecEscortAnnexCheck(String cardNo);

    /**
     * 插入一条审核结果
     * 
     * @param annexCheckInfo
     * @return
     */
    int InsertEscortAnnexCheck(EscortAnnexCheck annexCheckInfo);
}
