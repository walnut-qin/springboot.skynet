package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDeptPriv;

import lombok.Builder;
import lombok.Getter;

public interface SurgeryDeptPrivMapper {
    /**
     * 主键查询
     * 
     * @param icdCode  ICD-9手术编码
     * @param deptCode 科室编码
     * @return
     */
    SurgeryDeptPriv querySurgeryDeptPriv(String icdCode, String deptCode);

    /**
     * 多查询
     * 
     * @param key
     * @return
     */
    List<SurgeryDeptPriv> querySurgeryDeptPrivs(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 科室编码
         */
        private String icdCode;

        /**
         * 科室编码
         */
        private String deptCode;

        /**
         * 有效性标识
         */
        private Boolean valid;
    }
}
