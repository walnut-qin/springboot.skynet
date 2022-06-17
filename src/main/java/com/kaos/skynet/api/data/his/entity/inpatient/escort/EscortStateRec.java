package com.kaos.skynet.api.data.his.entity.inpatient.escort;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.utils.IntegerUtils;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：陪护证状态（KAOS.ESCORT_STATE_REC）
 */
@Getter
@Setter
@Builder
public class EscortStateRec {
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
    private StateEnum state;

    /**
     * 记录员编码
     */
    private String recEmplCode;

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
        if (arg0 instanceof EscortStateRec) {
            var that = (EscortStateRec) arg0;
            return StringUtils.equals(this.escortNo, that.escortNo) && IntegerUtils.equals(this.recNo, that.recNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(escortNo, recNo);
    }

    /**
     * 陪护状态
     */
    @Getter
    @AllArgsConstructor
    public enum StateEnum implements Enum {
        无核酸检测结果("0", "无核酸检测结果"), 等待院内核酸检测结果("1", "等待院内核酸检测结果"), 等待院外核酸检测结果审核("2", "等待院外核酸检测结果审核"), 生效中("3", "生效中"),
        注销("4", "注销"), 其他("5", "其他");
    
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
