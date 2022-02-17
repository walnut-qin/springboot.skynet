package com.kaos.his.entity.common;

import java.util.Date;

import com.kaos.his.enums.common.ReportTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.enums.common.ValidStateEnum;

/**
 * 统计小类到统计大类的对照关系
 */
public class FinComFeeCodeStat {
    /**
     * 报表代码 {@code 主键}
     */
    public ReportTypeEnum reportCode = null;

    /**
     * 最小费用类别 {@code 主键}
     */
    public MinFeeEnum feeCode = null;

    /**
     * 报表类型
     */
    public String reportType = null;

    /**
     * 报表类别
     */
    public String reportName = null;

    /**
     * 统计费用代码
     */
    public String feeStatCode = null;

    /**
     * 统计大类名称
     */
    public String feeStatName = null;

    /**
     * 统计大类
     */
    public String feeStatCate = null;

    /**
     * 执行科室代码 {@link FinComFeeCodeStat.AssociateEntity#execDept}
     */
    public String exeDeptCode = null;

    /**
     * 医保中心统计大类
     */
    public String centerStatCode = null;

    /**
     * 打印顺序
     */
    public String printOrder = null;

    /**
     * 有效标识
     */
    public ValidStateEnum validState = null;

    /**
     * 操作员 {@link FinComFeeCodeStat.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 执行科室 {@link FinComFeeCodeStat#exeDeptCode}
         */
        public DawnOrgDept execDept = null;

        /**
         * 操作员 {@link FinComFeeCodeStat#operCode}
         */
        public DawnOrgDept operEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
