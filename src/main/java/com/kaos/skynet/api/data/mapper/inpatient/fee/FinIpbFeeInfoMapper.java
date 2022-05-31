package com.kaos.skynet.api.data.mapper.inpatient.fee;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.fee.FinIpbFeeInfo;

import lombok.Builder;
import lombok.Getter;

public interface FinIpbFeeInfoMapper {
    /**
     * 查询指定时间段的费用项目
     * 
     * @param key
     * @return
     */
    List<FinIpbFeeInfo> queryFeeInfos(Key key);

    /**
     * 
     */
    @Getter
    @Builder
    public static class Key {
        /**
         * 执行科室编码, 等于 {@code null} 时，不作为判断条件
         */
        private String deptCode;

        /**
         * 计费开始时间, 等于 {@code null} 时，不作为判断条件
         */
        private LocalDateTime feeBeginDate;

        /**
         * 计费结束时间, 等于 {@code null} 时，不作为判断条件
         */
        private LocalDateTime feeEndDate;
    }
}
