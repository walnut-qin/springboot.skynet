package com.kaos.his.mapper.inpatient.escort;

import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.his.enums.EscortStateEnum;

public interface EscortMainInfoMapper {
    /**
     * 主键查询
     */
    EscortMainInfo queryEscortMainInfo(String escortNo);

    /**
     * 查询患者关联的陪护证主表信息
     * 
     * @param cardNo   患者卡号
     * @param happenNo 住院证序号
     * @param states   陪护证状态列表
     * @return
     */
    List<EscortMainInfo> queryPatientEscortMainInfos(String cardNo, Integer happenNo, List<EscortStateEnum> states);

    /**
     * 查询患者关联的陪护证主表信息
     * 
     * @param cardNo 陪护人卡号
     * @param states 陪护证状态列表
     * @return
     */
    List<EscortMainInfo> queryHelperEscortMainInfos(String cardNo, List<EscortStateEnum> states);

    /**
     * 插入陪护主记录
     * 
     * @param escortMainInfo
     * @return
     */
    int insertEscortMainInfo(EscortMainInfo escortMainInfo);
}
