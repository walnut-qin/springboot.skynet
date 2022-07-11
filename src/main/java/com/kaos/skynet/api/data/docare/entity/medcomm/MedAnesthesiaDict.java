package com.kaos.skynet.api.data.docare.entity.medcomm;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedAnesthesiaDict {
    /**
     * 住院号
     */
    private String anesName;

    /**
     * 住院标识，门诊为0
     */
    private AnesTypeEnum anesType;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MedAnesthesiaDict) {
            var that = (MedAnesthesiaDict) arg0;
            return StringUtils.equals(this.anesName, that.anesName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(anesName);
    }

    @Getter
    @AllArgsConstructor
    public enum AnesTypeEnum implements Enum {
        插管全麻("插管全麻", "插管全麻"),
        非插管全麻("非插管全麻", "非插管全麻"),
        复合麻醉("复合麻醉", "复合麻醉"),
        局麻("局麻", "局麻"),
        局麻强化MAC("局麻强化MAC", "局麻强化MAC"),
        神经阻滞("神经阻滞", "神经阻滞"),
        椎管内麻醉("椎管内麻醉", "椎管内麻醉");

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
