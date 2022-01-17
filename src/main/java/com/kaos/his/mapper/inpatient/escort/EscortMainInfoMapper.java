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
     * 查询陪护列表
     * 
     * @param patientCardNo
     * @param happenNo
     * @param helperCardNo
     * @param states
     * @return
     */
    List<EscortMainInfo> queryEscortMainInfos(String patientCardNo, Integer happenNo, String helperCardNo,
            List<EscortStateEnum> states);

    /**
     * 查询最后一张陪护（排序依据为注册时间）
     * 
     * @param patientCardNo
     * @param happenNo
     * @param helperCardNo
     * @param states
     * @return
     */
    EscortMainInfo queryLastEscortMainInfo(String patientCardNo, Integer happenNo, String helperCardNo,
            List<EscortStateEnum> states);

    /**
     * 插入陪护主记录
     * 
     * @param escortMainInfo
     * @return
     */
    int insertEscortMainInfo(EscortMainInfo escortMainInfo);
}
