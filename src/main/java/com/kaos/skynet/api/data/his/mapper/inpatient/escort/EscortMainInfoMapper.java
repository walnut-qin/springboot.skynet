package com.kaos.skynet.api.data.his.mapper.inpatient.escort;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface EscortMainInfoMapper {
    /**
     * 查询陪护证
     * 
     * @param escortNo 陪护证号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortMainInfo queryEscortMainInfo(String escortNo);

    /**
     * 查询陪护列表
     * 
     * @param key
     * @return
     */
    List<EscortMainInfo> queryEscortMainInfos(Key key);

    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 患者卡号, 等于 {@code null} 时，不作为判断条件
         */
        private String patientCardNo;

        /**
         * 患者卡号, 等于 {@code null} 时，不作为判断条件
         */
        private List<String> patientCardNos;

        /**
         * 住院证序号, 等于 {@code null} 时，不作为判断条件
         */
        private Integer happenNo;

        /**
         * 陪护人卡号, 等于 {@code null} 时，不作为判断条件
         */
        private String helperCardNo;

        /**
         * 陪护证状态, 等于 {@code null} 时，不作为判断条件
         */
        private List<StateEnum> states;
    }

    /**
     * 插入陪护主记录
     * 
     * @param escortMainInfo 陪护证实体，不必设置 {@code escortNo}，执行语句时自动生成
     * @return
     */
    Integer insertEscortMainInfo(EscortMainInfo escortMainInfo);
}
