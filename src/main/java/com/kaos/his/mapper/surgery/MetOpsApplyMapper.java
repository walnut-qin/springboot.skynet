package com.kaos.his.mapper.surgery;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.surgery.MetOpsApply;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.SurgeryStatusEnum;

import org.springframework.stereotype.Repository;

@Repository
public interface MetOpsApplyMapper {
    /**
     * 主键查询
     * 
     * @param operationNo
     * @return
     */
    MetOpsApply queryMetOpsApply(String operationNo);

    /**
     * 查询某个时段内的某个科室的某类状态的手术
     * 
     * @param deptCode  手术医生科室
     * @param beginDate 计划时间起点
     * @param endDate   计划时间终点
     * @param status    手术状态
     * @return
     */
    List<MetOpsApply> queryDeptMetOpsApplies(String deptCode, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status);

    /**
     * 查询某个时段内的某个院区的某类状态的手术
     * 
     * @param deptOwn   手术医生院区
     * @param beginDate 计划时间起点
     * @param endDate   计划时间终点
     * @param status    手术状态
     * @return
     */
    List<MetOpsApply> queryDeptOwnMetOpsApplies(DeptOwnEnum deptOwn, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status);
}