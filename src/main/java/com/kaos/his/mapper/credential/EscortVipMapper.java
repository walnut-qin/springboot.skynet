package com.kaos.his.mapper.credential;

import com.kaos.his.entity.credential.EscortVip;

import org.checkerframework.common.util.report.qual.ReportCreation;

@ReportCreation
public interface EscortVipMapper {
    /**
     * 主键查询，查询VIP实体
     * 
     * @param patientCardNo
     * @param happenNo
     * @return
     */
    public EscortVip QueryEscortVip(String patientCardNo, Integer happenNo);

    /**
     * 插入一条VIP记录
     * 
     * @param escortVip
     * @return
     */
    public int InsertEscortVip(EscortVip escortVip);
}
