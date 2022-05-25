package com.kaos.skynet.api.data.mapper.inpatient;

import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface FinIprInMainInfoMapper {
    /**
     * 查询住院主表记录
     * 
     * @param inpatientNo 住院流水号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIprInMainInfo queryInMainInfo(String inpatientNo);

    /**
     * 查询最后一个目标状态的住院实体，排序依据为住院证编号
     * 
     * @param cardNo   患者卡号; 等于 {@code null} 时，不作为判断条件
     * @param happenNo 住院证号; 等于 {@code null} 时，不作为判断条件
     * @param deptCode 科室编码; 等于 {@code null} 时，不作为判断条件
     * @param states   在院状态; 等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinIprInMainInfo> queryInMainInfos(Key key);

    /**
     * 检索Key值
     */
    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 患者卡号; 等于 {@code null} 时，不作为判断条件
         */
        private String cardNo;

        /**
         * 住院证序号; 等于 {@code null} 时，不作为判断条件
         */
        private Integer happenNo;

        /**
         * 科室编码; 等于 {@code null} 时，不作为判断条件
         */
        private String deptCode;

        /**
         * 在院状态; 等于 {@code null} 时，不作为判断条件
         */
        private List<InStateEnum> states;
    }
}
