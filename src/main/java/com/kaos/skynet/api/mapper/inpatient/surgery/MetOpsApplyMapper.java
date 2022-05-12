package com.kaos.skynet.api.mapper.inpatient.surgery;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.kaos.skynet.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.enums.impl.common.DeptOwnEnum;
import com.kaos.skynet.enums.impl.common.ValidStateEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryStatusEnum;

public interface MetOpsApplyMapper {
    /**
     * 主键查询
     * 
     * @param operationNo 手术号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsApply queryMetOpsApply(String operationNo);

    /**
     * 查询满足条件的手术申请记录
     * 
     * @param surgeryDeptCode 手术科室编码; 等于 {@code null} 时，不作为判断条件
     * @param beginPreDate    预约起始时间; 等于 {@code null} 时，不作为判断条件
     * @param endPreDate      预约结束时间; 等于 {@code null} 时，不作为判断条件
     * @param execStatus      审核状态; 等于 {@code null} 时，不作为判断条件
     * @param anesFlag        麻醉标识; 等于 {@code null} 时，不作为判断条件
     * @param valid           有效性标识; 等于 {@code null} 时，不作为判断条件
     * @param deptOwn         院区标识; 等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOpsApply> queryApplies(String surgeryDeptCode, LocalDateTime beginPreDate, LocalDateTime endPreDate,
            List<SurgeryStatusEnum> execStatus, Boolean anesFlag, ValidStateEnum valid, DeptOwnEnum deptOwn);

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
