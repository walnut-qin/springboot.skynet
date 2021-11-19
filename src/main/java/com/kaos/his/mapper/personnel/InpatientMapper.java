package com.kaos.his.mapper.personnel;

import java.util.List;

import com.kaos.his.entity.personnel.Inpatient;

import org.springframework.stereotype.Repository;

@Repository
public interface InpatientMapper {
    /**
     * 根据门诊号获取门诊患者实体
     * 
     * @param clinicNo 门诊号
     * @return 门诊患者实体
     */
    Inpatient GetInpatientByPatientNo(String patientNo);

    /**
     * 根据就诊卡号获取门诊患者实体列表
     * 
     * @param clinicNo 就诊卡号
     * @return 门诊患者实体
     */
    List<Inpatient> GetInpatientsByCardNo(String cardNo);
}
