package com.kaos.skynet.api.data.his.entity.inpatient.surgery;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术记录（XYHIS.MET_OPS_OPERATIONITEM）
 */
@Getter
@Setter
@Builder
public class MetOpsOperationItem {
    /**
     * 手术编号
     */
    private String operationNo;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsOperationItem) {
            var that = (MetOpsOperationItem) arg0;
            return StringUtils.equals(this.operationNo, that.operationNo)
                    && StringUtils.equals(this.itemCode, that.itemCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.operationNo, this.itemCode);
    }
}
