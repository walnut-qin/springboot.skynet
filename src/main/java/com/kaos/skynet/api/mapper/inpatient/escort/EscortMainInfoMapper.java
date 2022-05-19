package com.kaos.skynet.api.mapper.inpatient.escort;

import java.util.List;

import com.kaos.skynet.api.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;

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
     * @param patientCardNo 患者就诊卡号；等于 {@code null} 时，不作为判断条件
     * @param happenNo      住院证序号；等于 {@code null} 时，不作为判断条件
     * @param helperCardNo  陪护人就诊卡号；等于 {@code null} 时，不作为判断条件
     * @param states        陪护证状态列表；等于 {@code null} 时，不作为判断条件
     * @return 按照陪护证号正序后的陪护证列表（陪护证号依次递增）
     */
    List<EscortMainInfo> queryEscortMainInfos(String patientCardNo, Integer happenNo, String helperCardNo,
            List<EscortStateEnum> states);

    /**
     * 查询最后一张陪护
     * 
     * @param patientCardNo 患者就诊卡号；等于 {@code null} 时，不作为判断条件
     * @param happenNo      住院证序号；等于 {@code null} 时，不作为判断条件
     * @param helperCardNo  陪护人就诊卡号；等于 {@code null} 时，不作为判断条件
     * @param states        陪护证状态列表；等于 {@code null} 时，不作为判断条件
     * @return 按照陪护证号正序后（陪护证号依次递增），最后一张陪护证
     */
    EscortMainInfo queryLastEscortMainInfo(String patientCardNo, Integer happenNo, String helperCardNo,
            List<EscortStateEnum> states);

    /**
     * 插入陪护主记录
     * 
     * @param escortMainInfo 陪护证实体，不必设置 {@code escortNo}，执行语句时自动生成
     * @return
     */
    int insertEscortMainInfo(EscortMainInfo escortMainInfo);
}
