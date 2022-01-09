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
     * @param operationNo
     * @param status
     * @return
     */
    MetOpsApply queryMetOpsApply(String operationNo, List<SurgeryStatusEnum> status, ValidStateEnum valid);

    /**
     * 查询指定科室指定时段内的患者的手术，时间依据：PRE_DATE
     * 
     * @param deptCode
     * @param beginDate
     * @param endDate
     * @param status
     * @return
     */
    List<MetOpsApply> queryMetOpsAppliesInDept(String deptCode, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status, ValidStateEnum valid);

    /**
     * 查询指定科室指定时段内的患者的手术，时间依据：PRE_DATE
     * 
     * @param deptCode
     * @param beginDate
     * @param endDate
     * @param status
     * @return
     */
    List<MetOpsApply> queryMetOpsAppliesInDeptOwn(DeptOwnEnum deptOwn, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status, ValidStateEnum valid);
}
