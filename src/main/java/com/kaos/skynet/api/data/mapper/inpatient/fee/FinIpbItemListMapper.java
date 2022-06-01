package com.kaos.skynet.api.data.mapper.inpatient.fee;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.fee.FinIpbItemList;

import lombok.Builder;
import lombok.Getter;

public interface FinIpbItemListMapper {
    /**
     * 查询符合条件的记录列表
     * 
     * @param key 执行科室
     * @return
     */
    List<FinIpbItemList> queryItemLists(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 科室编码
         */
        private String deptCode;

        /**
         * 收费开始时间
         */
        private LocalDateTime feeBeginDate;

        /**
         * 收费结束时间
         */
        private LocalDateTime feeEndDate;
    }
}
