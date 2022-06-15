package com.kaos.skynet.api.data.entity.inpatient;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.utils.IntegerUtils;

import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：住院证（XYHIS.FIN_IPR_PREPAYIN）
 */
@Getter
@Setter
@Builder
public class FinIprPrepayIn {
    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 住院证编号
     */
    private Integer happenNo;

    /**
     * 预约床位号
     */
    private String bedNo;

    /**
     * 预约医师编码
     */
    private String preDocCode;

    /**
     * 预约住院科室
     */
    private String preDeptCode;

    /**
     * 预约入院时间
     */
    private Date preDate;

    /**
     * 开立医师编码
     */
    private String openDocCode;

    /**
     * 开立时间
     */
    private Date openDate;

    /**
     * 住院证状态
     */
    private InStateEnum state;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIprPrepayIn) {
            var that = (FinIprPrepayIn) arg0;
            return StringUtils.equals(this.cardNo, that.cardNo) && IntegerUtils.equals(this.happenNo, that.happenNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardNo, happenNo);
    }

    @Getter
    @AllArgsConstructor
    public enum InStateEnum implements Enum {
        预约("0", "预约"), 作废("1", "作废"), 转住院("2", "转住院"), 签床("3", "签床"), 预住院预约("4", "预住院预约");
    
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
