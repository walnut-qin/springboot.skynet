package com.kaos.skynet.api.data.mapper.outpatient.fee;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.entity.outpatient.fee.FinOpbFeeDetail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface FinOpbFeeDetailMapper {
    /**
     * 条件查询
     * 
     * @param key
     * @return
     */
    List<FinOpbFeeDetail> queryFeeDetails(Key key);

    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 门诊号, 等于 {@code null} 时，不作为条件, 但是当 {@link Key#cardNo} 也为空时, 将 IS NULL 作为条件
         */
        private String clinicCode;

        /**
         * 就诊卡号, 等于 {@code null} 时，不作为条件, 但是当 {@link Key#clinicCode} 也为空时, 将 IS NULL 作为条件
         */
        private String cardNo;

        /**
         * 收费项目编码, 等于 {@code null} 时，不作为条件
         */
        private String itemCode;

        /**
         * 开始时间, 等于 {@code null} 时，不作为条件
         */
        private LocalDateTime operBeginDate;

        /**
         * 结束时间, 等于 {@code null} 时，不作为条件
         */
        private LocalDateTime operEndDate;
    }
}
