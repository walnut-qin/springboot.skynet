package com.kaos.his.controller.inpatient.surgery.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.kaos.his.enums.impl.inpatient.surgery.SurgeryStatusEnum;

import org.springframework.validation.annotation.Validated;

@Validated
public class QueryAppliesReq {
    /**
     * 科室编码
     */
    public String deptCode = null;

    /**
     * 手术室编码
     */
    public String roomNo = null;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    public Date beginDate = null;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    public Date endDate = null;

    /**
     * 状态清单
     */
    public List<SurgeryStatusEnum> states = null;
}
