package com.kaos.skynet.api.data.his.mapper.inpatient.fee;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.fee.FinIpbMedicineList;

import lombok.Builder;
import lombok.Getter;

public interface FinIpbMedicineListMapper {
    /**
     * 查询药品明细
     * 
     * @param key 执行科室
     * @return
     */
    List<FinIpbMedicineList> queryMedicineLists(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 科室编码
         */
        private String deptCode;

        /**
         * 费用开始时间
         */
        private LocalDateTime beginFeeDate;

        /**
         * 费用结束时间
         */
        private LocalDateTime endFeeDate;
    }
}
