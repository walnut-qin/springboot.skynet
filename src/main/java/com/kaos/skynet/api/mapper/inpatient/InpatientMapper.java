package com.kaos.skynet.api.mapper.inpatient;

import java.util.List;

import com.kaos.skynet.api.entity.inpatient.Inpatient;
import com.kaos.skynet.enums.inpatient.InStateEnum;

public interface InpatientMapper {
    /**
     * 主键查询
     * 
     * @param inpatientNo 住院号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    Inpatient queryInpatient(String inpatientNo);

    /**
     * 查询住院列表
     * 
     * @param cardNo   住院号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param happenNo 住院号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<Inpatient> queryInpatientsWithPrepayIn(String cardNo, Integer happenNo);

    /**
     * 查询最后一个目标状态的住院实体，排序依据为住院证编号
     * 
     * @param cardNo   患者卡号；等于 {@code null} 时，不作为判断条件
     * @param deptCode 科室编码；等于 {@code null} 时，不作为判断条件
     * @param states   在院状态；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<Inpatient> queryInpatients(String cardNo, String deptCode, List<InStateEnum> states);
}
