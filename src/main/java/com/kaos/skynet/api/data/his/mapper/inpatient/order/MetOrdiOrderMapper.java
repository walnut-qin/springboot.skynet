package com.kaos.skynet.api.data.his.mapper.inpatient.order;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.order.MetOrdiOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface MetOrdiOrderMapper {
    /**
     * 主键查询
     * 
     * @param moOrder 医嘱单号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOrdiOrder queryOrder(String moOrder);

    /**
     * 查询医嘱列表
     * 
     * @param key
     * @return
     */
    List<MetOrdiOrder> queryOrders(Key key);

    /**
     * 检索键值
     */
    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 住院流水号, 等于 {@code null} 时，不作为判断条件
         */
        private String inpatientNo;

        /**
         * 医嘱编码, 等于 {@code null} 时，不作为判断条件
         */
        private String termId;

        /**
         * 开立医嘱时间 - 开始时间, 等于 {@code null} 时，不作为判断条件
         */
        private LocalDateTime beginMoDate;

        /**
         * 开立医嘱时间 - 结束时间, 等于 {@code null} 时，不作为判断条件, 等于 {@code null} 时，不作为判断条件
         */
        private LocalDateTime endMoDate;
    }
}
