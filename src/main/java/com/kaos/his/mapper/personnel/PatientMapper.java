package com.kaos.his.mapper.personnel;

import com.kaos.his.entity.personnel.Patient;

import org.springframework.stereotype.Repository;

@Repository
public interface PatientMapper {
    /**
     * 根据就诊卡号获取患者实体
     * 
     * @param cardNo 就诊卡号
     * @return 患者实体
     */
    Patient QueryPatient(String cardNo);
}
