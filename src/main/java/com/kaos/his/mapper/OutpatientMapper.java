package com.kaos.his.mapper;

import java.util.List;

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

    /**
     * 根据就诊卡号获取门诊患者实体列表
     * 
     * @param clinicNo 就诊卡号
     * @return 门诊患者实体
     */
    List<Outpatient> GetOutpatientsByCardNo(String cardNo);
}
