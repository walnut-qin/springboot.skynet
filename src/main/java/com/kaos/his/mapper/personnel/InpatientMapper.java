package com.kaos.his.mapper.personnel;

import java.util.List;

import com.kaos.his.entity.personnel.Inpatient;

import org.springframework.stereotype.Repository;

@Repository
public interface InpatientMapper {
    /**
     * 根据门诊号获取住院患者实体
     * 
     * @param clinicNo 门诊号
     * @return 住院患者实体
     */
    Inpatient GetInpatientByPatientNo(String patientNo);

    /**
     * 根据住院证信息获取住院患者实体
     * 
     * @param cardNo   患者就诊卡号
     * @param happenNo 住院证序号
     * @return 住院患者实体
     */
    Inpatient GetInpatientByCardNoAndHappenNo(String cardNo, Integer happenNo);

    /**
     * 根据就诊卡号获取住院患者实体列表
     * 
     * @param clinicNo 就诊卡号
     * @return 住院患者实体
     */
    List<Inpatient> GetInpatientsByCardNo(String cardNo);
}
