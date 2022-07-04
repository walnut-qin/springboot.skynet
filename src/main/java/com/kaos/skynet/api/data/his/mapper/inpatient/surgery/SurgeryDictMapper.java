package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict;
import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;

import lombok.Builder;
import lombok.Getter;

public interface SurgeryDictMapper {
    /**
     * 主键查询
     * 
     * @param icdCode ICD-9手术编码
     * @return
     */
    SurgeryDict querySurgeryDict(String icdCode);

    /**
     * 多值查询
     * 
     * @param key
     * @return
     */
    List<SurgeryDict> querySurgeryDicts(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * icd编码
         */
        private List<String> icdCodes;

        /**
         * 手术等级
         */
        private SurgeryLevelEnum level;

        /**
         * 有效性标识
         */
        private Boolean valid;
    }

    /**
     * 插入操作
     * 
     * @param surgeryDict
     * @return
     */
    Integer insertSurgeryDict(SurgeryDict surgeryDict);

    /**
     * 变更操作
     * 
     * @param surgeryDict
     * @return
     */
    Integer updateSurgeryDict(SurgeryDict surgeryDict);
}
