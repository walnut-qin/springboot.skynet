package com.kaos.his.mapper;

import com.kaos.his.entity.personnel.Outpatient;

import org.springframework.stereotype.Repository;

@Repository
public interface OutpatientMapper {
    /**
     * 根据门诊号获取门诊患者实体
     * 
     * @param clinicNo 门诊号
     * @return 门诊患者实体
     */
    Outpatient GetOutpatientByClinicCode(String clinicCode);
}
