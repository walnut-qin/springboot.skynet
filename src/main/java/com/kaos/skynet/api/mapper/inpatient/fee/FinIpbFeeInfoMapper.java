package com.kaos.skynet.api.mapper.inpatient.fee;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.entity.inpatient.fee.FinIpbFeeInfo;

public interface FinIpbFeeInfoMapper {
    /**
     * 查询指定时间段的费用项目
     * 
     * @param deptCode  执行科室编码, 等于 {@code null} 时，不作为判断条件
     * @param beginDate 计费开始时间, 等于 {@code null} 时，不作为判断条件
     * @param endDate   计费结束时间, 等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinIpbFeeInfo> queryFeeInfos(String deptCode, Date beginDate, Date endDate);
}
