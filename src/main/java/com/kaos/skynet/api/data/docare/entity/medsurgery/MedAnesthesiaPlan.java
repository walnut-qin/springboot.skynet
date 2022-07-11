package com.kaos.skynet.api.data.docare.entity.medsurgery;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedAnesthesiaPlan {
    /**
     * 住院号
     */
    private String patientId;

    /**
     * 住院标识，门诊为0
     */
    private Integer visitId;

    /**
     * 手术号
     */
    private Integer operId;

    /**
     * ASA分级
     */
    private AsaGradeEnum asaGrade;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MedAnesthesiaPlan) {
            var that = (MedAnesthesiaPlan) arg0;
            return StringUtils.equals(this.patientId, that.patientId)
                    && IntegerUtils.equals(this.visitId, that.visitId)
                    && IntegerUtils.equals(this.operId, that.operId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patientId, visitId, operId);
    }

    @Getter
    @AllArgsConstructor
    public enum AsaGradeEnum implements Enum {
        I("Ⅰ", "I"),
        II("Ⅱ", "II"),
        III("Ⅲ", "III"),
        IV("Ⅳ", "IV"),
        V("Ⅴ", "V"),
        VI("Ⅵ", "VI");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}