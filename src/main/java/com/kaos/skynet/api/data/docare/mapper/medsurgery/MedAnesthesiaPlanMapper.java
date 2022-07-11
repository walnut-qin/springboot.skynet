package com.kaos.skynet.api.data.docare.mapper.medsurgery;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kaos.skynet.api.data.docare.entity.medsurgery.MedAnesthesiaPlan;

@DS("docare")
public interface MedAnesthesiaPlanMapper {
    /**
     * 主键查询
     * 
     * @param patientId 住院号, 值为 {@code null} 时, 将 IS NULL 设置为条件
     * @param visitId   一次入院的手术次序号, 值为 {@code null} 时, 将 IS NULL 设置为条件
     * @param operId    手术编号, 值为 {@code null} 时, 将 IS NULL 设置为条件
     * @return
     */
    MedAnesthesiaPlan queryAnesthesiaPlan(String patientId, Integer visitId, Integer operId);
}
