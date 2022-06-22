package com.kaos.skynet.api.data.his.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EscortActionRec {
    /**
     * 陪护证编号
     */
    private String escortNo;

    /**
     * 状态序号
     */
    private Integer recNo;

    /**
     * 状态
     */
    private ActionEnum action;

    /**
     * 记录时间
     */
    private LocalDateTime recDate;

    /**
     * 备注
     */
    private String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortActionRec) {
            var that = (EscortActionRec) arg0;
            return StringUtils.equals(this.escortNo, that.escortNo) && IntegerUtils.equals(this.recNo, that.recNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(escortNo, recNo);
    }

    /**
     * 陪护行为字典
     */
    @Getter
    @AllArgsConstructor
    public enum ActionEnum implements Enum {
        进入("I", "进入"), 外出("O", "外出");
    
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
