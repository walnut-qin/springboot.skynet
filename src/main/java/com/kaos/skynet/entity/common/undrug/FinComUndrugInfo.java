package com.kaos.skynet.entity.common.undrug;

import java.util.Date;

import com.kaos.skynet.entity.common.DawnOrgDept;
import com.kaos.skynet.entity.common.DawnOrgEmpl;
import com.kaos.skynet.enums.common.ItemGradeEnum;
import com.kaos.skynet.enums.common.MinFeeEnum;
import com.kaos.skynet.enums.common.SysClassEnum;
import com.kaos.skynet.enums.common.ValidStateEnum;

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
     * 手术规模
     */
    public String operateType = null;

    /**
     * 是否有物资项目与之对照(1有，0没有)
     */
    public Boolean collateFlag = null;

    /**
     * 备注
     */
    public String mark = null;

    /**
     * 操作员 {@link FinComUndrugInfo.AssociateEntity#operEmpl}
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 疾病分类(开立检验项目时使用)
     */
    public String diseaseClass = null;

    /**
     * 农合医保类别
     */
    public String specialDept = null;

    /**
     * 是否需要打印知情同意书
     */
    public Boolean conSentFlag = null;

    /**
     * 住院医保类别
     */
    public String mark1 = null;

    /**
     * 检查要求(襄阳保存二汽医保类别)
     */
    public String mark2 = null;

    /**
     * 注意事项(开立检查申请单时使用) 暂用为半价处理标示 1奇数全家偶数半价 2首个全价之后全部半价
     */
    public String mark3 = null;

    /**
     * 检查申请单名称 暂用为半价系数
     */
    public String mark4 = null;

    /**
     * 是否需要预约
     */
    public Boolean needBespeak = null;

    /**
     * 项目范围
     */
    public String itemArea = null;

    /**
     * 项目例外
     */
    public String itemNoArea = null;

    /**
     * 单位标识 true: 组套, false: 明细
     */
    public Boolean unitFlag = null;

    /**
     * 适用范围(0: 全部, 1: 门诊, 2: 住院)
     */
    public String applicabilityArea = null;

    /**
     * 允许开立的科室列表，ALL表示全有
     */
    public String deptList = null;

    /**
     * 物价费用类别
     */
    public String itemPriceType = null;

    /**
     * 该项目是否打印医嘱单
     */
    public Boolean orderPrintTag = null;

    /**
     * 是否在打印医嘱单的时候显示该项目的频次
     */
    public Boolean showFre = null;

    /**
     * 医院等级
     */
    public String hosLevel = null;

    /**
     * 用于标注SPD的高低值耗材，1高值、2低值。0非高非低
     */
    public String spdGd = null;

    /**
     * 院区标识
     */
    public String branch = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 执行科室 {@link FinComUndrugInfo#execDeptCode}
         */
        public DawnOrgDept execDept = null;

        /**
         * 操作员 {@link FinComUndrugInfo#operCode}
         */
        public DawnOrgEmpl operEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
