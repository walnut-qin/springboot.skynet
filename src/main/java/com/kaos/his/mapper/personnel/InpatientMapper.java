package com.kaos.his.mapper.personnel;

import java.util.List;

import com.kaos.his.entity.personnel.Inpatient;

import org.springframework.stereotype.Repository;

@Repository
public interface InpatientMapper {
    /**
     * 根据门诊号获取住院患者实体
     * 
     * @param patientNo 住院号
     * @return 住院患者实体
     */
    Inpatient QueryInpatient(String patientNo);

    /**
     * 重载形式1
     * 
     * @param cardNo   患者就诊卡号
     * @param happenNo 住院证序号
     * @return 住院患者实体
     */
    Inpatient QueryInpatientR1(String cardNo, Integer happenNo);

    /**
     * 根据就诊卡号获取住院患者实体列表
     * 
     * @param cardNo 就诊卡号
     * @return 住院患者实体
     */
    List<Inpatient> QueryInpatients(String cardNo);

    /**
     * 查询指定科室的住院患者
     * 
     * @param deptCode
     * @return
     */
    List<Inpatient> QueryDeptInpatients(String deptCode);
}
