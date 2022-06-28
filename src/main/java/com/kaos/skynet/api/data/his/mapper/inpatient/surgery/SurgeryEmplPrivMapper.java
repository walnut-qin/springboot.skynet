package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryEmplPriv;

import lombok.Builder;
import lombok.Getter;

public interface SurgeryEmplPrivMapper {
    /**
     * 主键查询
     * 
     * @param icdCode  ICD-9手术编码
     * @param emplCode 职员编码
     * @return
     */
    SurgeryEmplPriv querySurgeryEmplPriv(String icdCode, String emplCode);

    /**
     * 多查询
     * 
     * @param key
     * @return
     */
    List<SurgeryEmplPriv> querySurgeryEmplPrivs(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 科室编码
         */
        private String emplCode;

        /**
         * 有效性标识
         */
        private Boolean valid;
    }
}
