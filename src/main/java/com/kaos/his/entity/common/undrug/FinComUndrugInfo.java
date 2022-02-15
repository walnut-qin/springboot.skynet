package com.kaos.his.entity.common.undrug;

import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.enums.common.ItemGradeEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.enums.common.SysClassEnum;
import com.kaos.his.enums.common.ValidStateEnum;

/**
 * 非药品信息表
 */
public class FinComUndrugInfo {
    /**
     * 非药品编码
     */
    public String itemCode = null;

    /**
     * 非药品名称
     */
    public String itemName = null;

    /**
     * 系统类别
     */
    public SysClassEnum sysClass = null;

    /**
     * 最小费用类别
     */
    public MinFeeEnum feeCode = null;

    /**
     * 输入码
     */
    public String inputCode = null;

    /**
     * 拼音码
     */
    public String spellCode = null;

    /**
     * 五笔码
     */
    public String wbCode = null;

    /**
     * 国家编码
     */
    public String internalCode = null;

    /**
     * 国际编码
     */
    public String internationalCode = null;

    /**
     * 三甲价格
     */
    public Double unitPrice = null;

    /**
     * 儿童价格
     */
    public Double unitPrice1 = null;

    /**
     * 特诊价格
     */
    public Double unitPrice2 = null;

    /**
     * 价格3
     */
    public Double unitPrice3 = null;

    /**
     * 价格4
     */
    public Double unitPrice4 = null;

    /**
     * 急诊加成比例
     */
    public Double emergencyScale = null;

    /**
     * 单位
     */
    public String stockUnit = null;

    /**
     * 省限制
     */
    public Boolean specialFlag = null;

    /**
     * 市限制
     */
    public Boolean specialFlag1 = null;

    /**
     * 自费项目
     */
    public Boolean specialFlag2 = null;

    /**
     * 特定治疗项目
     */
    public Boolean specialFlag3 = null;

    /**
     * 中山一：是否强制出单
     */
    public Boolean specialFlag4 = null;

    /**
     * 计划生育标记
     */
    public Boolean familyPlan = null;

    /**
     * 用来保存老系统已有的自定义码用于医保上传
     */
    public String specialItem = null;

    /**
     * 甲乙丙类标记
     */
    public ItemGradeEnum itemGrad = null;

    /**
     * 是否需要确认
     */
    public Boolean confirmFlag = null;

    /**
     * 有效状态
     */
    public ValidStateEnum validState = null;

    /**
     * 规格
     */
    public String spec = null;

    /**
     * 执行科室 {@link FinComUndrugInfo.AssociateEntity#execDept}
     */
    public String execDeptCode = null;

    /**
     * 设备编号 用 | 区分
     */
    public String facilityNo = null;

    /**
     * 默认检查部位或标本
     */
    public String defaultSample = null;

    /**
     * 手术编码
     */
    public String operateCode = null;

    /**
     * 手术分类
     */
    public String operateKind = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 执行科室 {@link FinComUndrugInfo#execDeptCode}
         */
        public DawnOrgDept execDept = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
