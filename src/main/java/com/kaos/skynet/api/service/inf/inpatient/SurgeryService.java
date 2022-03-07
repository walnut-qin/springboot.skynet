package com.kaos.skynet.api.service.inf.inpatient;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryStatusEnum;

public interface SurgeryService {
    /**
     * 查询符合条件的手术
     * 
     * @param deptCode  科室编码
     * @param roomNo    手术室编码
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param status    状态列表
     * @return
     */
    List<MetOpsApply> queryApplies(String deptCode, String roomNo, Date beginDate, Date endDate,
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
