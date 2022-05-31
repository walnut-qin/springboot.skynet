package com.kaos.skynet.api.data.entity.inpatient.order;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.enums.inpatient.order.MetOrdiOrderStateEnum;
import com.kaos.skynet.core.type.Entity;
import com.kaos.skynet.core.type.utils.StringUtils;

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
    private MetOrdiOrderStateEnum state;

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
}
