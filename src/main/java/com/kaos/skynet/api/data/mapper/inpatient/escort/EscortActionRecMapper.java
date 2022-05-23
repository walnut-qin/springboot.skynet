package com.kaos.skynet.api.data.mapper.inpatient.escort;

import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec;

public interface EscortActionRecMapper {
    /**
     * 查询行为列表，按照登记顺序排序
     * 
     * @param escortNo 陪护证号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<EscortActionRec> queryActions(String escortNo);

    /**
     * 添加一条动作
     * 
     * @param escortActionRec 动作实体，不必设置 {@code recNo}，执行语句时自动生成
     * @return
     */
    Integer insertAction(EscortActionRec escortActionRec);
}
