package com.kaos.skynet.api.data.his.mapper.inpatient;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.FinIprPrepayIn;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprPrepayIn.InStateEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface FinIprPrepayInMapper {
    /**
     * 主键查询
     * 
     * @param cardNo   就诊卡号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param happenNo 住院证序号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIprPrepayIn queryPrepayIn(String cardNo, Integer happenNo);

    /**
     * 查询患者所有陪护证
     * 
     * @param key 就诊卡号
     * @return
     */
    List<FinIprPrepayIn> queryPrepayIns(Key key);

    /**
     * 多查询索引
     */
    @Getter
    @Setter
    @Builder
    public static class Key{
        /**
         * 就诊卡号, 等于 {@code null} 时，不作为判断条件
         */
        private String cardNo;

        /**
         * 状态清单, 等于 {@code null} 时，不作为判断条件
         */
        private List<InStateEnum> states;
    }
}
