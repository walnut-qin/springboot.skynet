package com.kaos.skynet.api.data.entity.common.fee;

import java.util.Date;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.MinFeeEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 统计小类到统计大类的对照关系
 */
@Getter
@Setter
@Builder
public class FinComFeeCodeStat {
    /**
     * 报表代码
     */
    private ReportTypeEnum reportCode;

    /**
     * 最小费用类别
     */
    private MinFeeEnum feeCode;

    /**
     * 报表类型
     */
    private String reportType;

    /**
     * 报表类别
     */
    private String reportName;

    /**
     * 统计费用代码
     */
    private String feeStatCode;

    /**
     * 统计大类名称
     */
    private String feeStatName;

    /**
     * 统计大类
     */
    private String feeStatCate;

    /**
     * 执行科室代码
     */
    private String exeDeptCode;

    /**
     * 医保中心统计大类
     */
    private String centerStatCode;

    /**
     * 打印顺序
     */
    private String printOrder;

    /**
     * 有效标识
     */
    private ValidEnum validState;

    /**
     * 操作员
     */
    private String operCode;

    /**
     * 操作时间
     */
    private Date operDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinComFeeCodeStat) {
            var that = (FinComFeeCodeStat) arg0;
            return this.reportCode == that.reportCode && this.feeCode == that.feeCode;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reportCode, feeCode);
    }

    /**
     * 专用枚举
     */
    @AllArgsConstructor
    public static enum ReportTypeEnum implements Enum {
        病案首页("BA01", "病案首页"),
        门诊发票("MZ01", "门诊发票"),
        门诊统计("MZ02", "门诊统计"),
        门诊日结报表("MZRJ", "门诊日结报表"),
        住院发票("ZY01", "住院发票"),
        财务住院统计("ZY04", "财务住院统计"),
        住院日结报表("ZYRJ", "住院日结报表"),
        住院统计("ZYTJ", "住院统计");

        /**
         * 数据库存值
         */
        @Getter
        private String value;

        /**
         * 描述存值
         */
        @Getter
        private String description;
    }
}
