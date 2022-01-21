package com.kaos.his.mapper.inpatient.surgery;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.surgery.MetOpsApply;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.SurgeryStatusEnum;
import com.kaos.his.enums.ValidStateEnum;

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
     * @param deptCode  科室编码；等于 {@code null} 时，不作为判断条件
     * @param beginDate 开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate   结束时间；等于 {@code null} 时，不作为判断条件
     * @param status    状态清单；等于 {@code null} 时，不作为判断条件
     * @param valid     有效标识；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOpsApply> queryMetOpsAppliesInDept(String deptCode, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status, ValidStateEnum valid);

    /**
     * 查询指定科室指定时段内的患者的手术，时间依据：PRE_DATE
     * 
     * @param deptOwn   院区编码；等于 {@code null} 时，不作为判断条件
     * @param beginDate 开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate   结束时间；等于 {@code null} 时，不作为判断条件
     * @param status    状态清单；等于 {@code null} 时，不作为判断条件
     * @param valid     有效标识；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOpsApply> queryMetOpsAppliesInDeptOwn(DeptOwnEnum deptOwn, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status, ValidStateEnum valid);
}
