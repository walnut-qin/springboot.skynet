package com.kaos.skynet.api.data.his.entity.inpatient.order;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Entity;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MetOrdiOrder extends Entity {
    /**
     * 医嘱号
     */
    private String moOrder;

    /**
     * 住院流水号
     */
    private String inpatientNo;

    /**
     * 术语编码
     */
    private String termId;

    /**
     * 术语名称
     */
    private String termName;

    /**
     * 医嘱状态
     */
    private OrderStateEnum state;

    /**
     * 医嘱开立医生
     */
    private String moDocCode;

    /**
     * 医嘱开立时间
     */
    private Date moDate;

    /**
     * 医嘱执行科室
     */
    private String execDeptCode;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOrdiOrder) {
            var that = (MetOrdiOrder) arg0;
            return StringUtils.equals(this.moOrder, that.moOrder);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.moOrder);
    }

    /**
     * 医嘱状态字典
     */
    @Getter
    @AllArgsConstructor
    public enum OrderStateEnum implements Enum {
        待审核("10", "待审核"),
        暂存("15", "暂存"),
        签发("20", "已提交(签发)"),
        已接收("30", "已接收"),
        已执行("40", "已执行"),
        已完成("50", "已完成"),
        停止作废("90", "停止作废");

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
