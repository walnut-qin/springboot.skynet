package com.kaos.skynet.api.service.inf.inpatient;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.api.data.entity.inpatient.surgery.MetOpsApply.StatusEnum;

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
    List<MetOpsApply> queryApplies(String deptCode, String roomNo, LocalDateTime beginDate, LocalDateTime endDate,
            List<StatusEnum> status);
}
