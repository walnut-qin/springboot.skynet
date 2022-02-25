package com.kaos.his.mapper.inpatient.surgery;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.surgery.MetOpsApply;
import com.kaos.his.enums.impl.common.ValidStateEnum;
import com.kaos.his.enums.impl.inpatient.surgery.SurgeryStatusEnum;

public interface MetOpsApplyMapper {
    /**
     * 主键查询
     * 
     * @param operationNo 手术号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsApply queryMetOpsApply(String operationNo);

    /**
     * 查询符合条件的手术
     * 
     * @param lDate  开始时间；等于 {@code null} 时，不作为判断条件
     * @param rDate  结束时间；等于 {@code null} 时，不作为判断条件
     * @param status 状态清单；等于 {@code null} 时，不作为判断条件
     * @param valid  有效标识；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOpsApply> queryApplies(Date beginDate, Date endDate, List<SurgeryStatusEnum> status, ValidStateEnum valid);

    /**
     * 查询患者手术
     * 
     * @param patientNo 住院号; 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param beginDate 开始时间, 等于 {@code null} 时，不作为判断条件
     * @param endDate   结束时间, 等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOpsApply> queryPatientMetOpsApplies(String patientNo, Date beginDate, Date endDate, ValidStateEnum valid);
}
