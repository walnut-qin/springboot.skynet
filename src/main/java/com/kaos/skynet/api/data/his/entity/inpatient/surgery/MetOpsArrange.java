package com.kaos.skynet.api.data.his.entity.inpatient.surgery;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术人员安排（XYHIS.MET_OPS_ARRANGE）
 */
@Getter
@Setter
@Builder
public class MetOpsArrange {
    /**
     * 手术编号
     */
    private String operationNo;

    /**
     * 角色
     */
    private RoleEnum role;

    /**
     * 职工编码
     */
    private String emplCode;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsArrange) {
            var that = (MetOpsArrange) arg0;
            return StringUtils.equals(this.operationNo, that.operationNo)
                    && this.role == that.role
                    && StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.operationNo, this.role, this.emplCode);
    }

    /**
     * 手术安排角色字典
     */
    @Getter
    @AllArgsConstructor
    public enum RoleEnum implements Enum {
        AnaesthesiaHelper("AnaesthesiaHelper", "麻醉助手"), Anaesthetist("Anaesthetist", "麻醉医生"), Helper1("Helper1", "手术助手"),
        Helper2("Helper2", "手术助手"), Helper3("Helper3", "手术助手"), ItinerantNurse("ItinerantNurse", "巡回护士"),
        ItinerantNurse1("ItinerantNurse1", "巡回护士"), Operator("Operator", "主刀医师"),
        WashingHandNurse("WashingHandNurse", "洗手护士"),
        WashingHandNurse1("WashingHandNurse1", "洗手护士");
    
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
