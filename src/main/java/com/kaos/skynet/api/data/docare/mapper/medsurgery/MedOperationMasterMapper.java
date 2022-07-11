package com.kaos.skynet.api.data.docare.mapper.medsurgery;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kaos.skynet.api.data.docare.entity.medsurgery.MedOperationMaster;
import com.kaos.skynet.api.data.docare.entity.medsurgery.MedOperationMaster.OperStatusEnum;

import lombok.Getter;
import lombok.Builder;

@DS("docare")
public interface MedOperationMasterMapper {
    /**
     * 主键查询
     * 
     * @param patientId 住院号, 值为 {@code null} 时, 将 IS NULL 设置为条件
     * @param visitId   一次入院的手术次序号, 值为 {@code null} 时, 将 IS NULL 设置为条件
     * @param operId    手术编号, 值为 {@code null} 时, 将 IS NULL 设置为条件
     * @return
     */
    MedOperationMaster queryOperationMaster(String patientId, Integer visitId, Integer operId);

    /**
     * 批量查询手术
     * 
     * @param key
     * @return
     */
    List<MedOperationMaster> queryOperationMasters(Key key);

    /**
     * 查询键值
     */
    @Getter
    @Builder
    public static class Key {
        /**
         * 手术状态清单
         */
        private LocalDateTime beginInDateTime;

        /**
         * 手术状态清单
         */
        private LocalDateTime endInDateTime;

        /**
         * 手术状态清单
         */
        private List<OperStatusEnum> operStatus;

        /**
         * 手术状态清单
         */
        private List<OperStatusEnum> negOperStatus;
    }
}
