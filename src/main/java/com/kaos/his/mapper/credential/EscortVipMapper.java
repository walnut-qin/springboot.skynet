package com.kaos.his.mapper.credential;

import com.kaos.his.entity.credential.EscortVip;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortVipMapper {
    /**
     * 主键查询，查询VIP实体
     * 
     * @param patientCardNo
     * @param happenNo
     * @return
     */
    EscortVip QueryEscortVip(String patientCardNo, Integer happenNo);

    /**
     * 插入一条VIP记录
     * 
     * @param escortVip
     * @return
     */
    int InsertEscortVip(EscortVip escortVip);
}
