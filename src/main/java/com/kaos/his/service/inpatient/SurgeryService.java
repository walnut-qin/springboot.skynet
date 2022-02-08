package com.kaos.his.service.inpatient;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.surgery.MetOpsApply;
import com.kaos.his.enums.inpatient.surgery.SurgeryStatusEnum;

public interface SurgeryService {
    /**
     * 查询指定科室内的手术
     * 
     * @param deptCode 科室编码
     * @return 手术申请记录
     */
    List<MetOpsApply> queryMetOpsAppliesInDept(String deptCode, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status);

    /**
     * 查询手术申请号
     * 
     * @param patientNo 住院号
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param valid     有效状态
     * @return
     */
    String queryValidApplyNo(String patientNo, Date beginDate, Date endDate);
}
