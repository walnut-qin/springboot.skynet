package com.kaos.skynet.api.data.his.mapper.outpatient.fee;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.outpatient.fee.FinOprPayModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface FinOprPayModelMapper {
    /**
     * 查询线上记录
     * 
     * @param key
     * @return
     */
    List<FinOprPayModel> queryPayModels(Key key);

    /**
     * 检索Key值
     */
    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 就诊卡号或住院号; 等于 {@code null} 时，将IS NULL作为条件
         */
        private String patientId;

        /**
         * 平台结算号; 等于 {@code null} 时，不作为条件
         */
        private String referNo;

        /**
         * 发票号; 等于 {@code null} 时，不作为条件
         */
        private String invoiceNo;
    }
}
