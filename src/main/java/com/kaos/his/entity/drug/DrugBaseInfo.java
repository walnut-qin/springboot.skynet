package com.kaos.his.entity.drug;

import java.util.Date;

import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.DrugItemGradeEnum;
import com.kaos.his.enums.DrugShiftTypeEnum;
import com.kaos.his.enums.DrugValidStateEnum;

/**
 * 药品基本信息，对应药品账页
 */
public class DrugBaseInfo {
    /**
     * 药品编码
     */
    public String drugCode = null;

    /**
     * 商品名称
     */
    public String tradeName = null;

    /**
     * 商品名拼音码
     */
    public String spellCode = null;

    /**
     * 商品名五笔码
     */
    public String wbCode = null;

    /**
     * 商品名自定义码
     */
    public String cusCode = null;

    /**
     * 通用名
     */
    public String regName = null;

    /**
     * 通用名拼音码
     */
    public String regSpellCode = null;

    /**
     * 通用名五笔码
     */
    public String regWbCode = null;

    /**
     * 通用名自定义码
     */
    public String regCusCode = null;

    /**
     * 学名
     */
    public String fmlName = null;

    /**
     * 学名拼音码
     */
    public String fmlSpellCode = null;

    /**
     * 学名五笔码
     */
    public String fmlWbCode = null;

    /**
     * 学名自定义码
     */
    public String fmlCusCode = null;

    /**
     * 别名
     */
    public String otherName = null;

    /**
     * 别名拼音码
     */
    public String otherSpellCode = null;

    /**
     * 别名五笔码
     */
    public String otherWbCode = null;

    /**
     * 别名自定义码
     */
    public String otherCusCode = null;

    /**
     * 住院医保类别
     */
    public String englishRegular = null;

    /**
     * 农合医保类别
     */
    public String englishOther = null;

    /**
     * 英文名
     */
    public String englishName = null;

    /**
     * 国际编码
     */
    public String intlCode = null;

    /**
     * 国家编码
     */
    public String nlCode = null;

    /**
     * 系统类别
     */
    public String classCode = null;

    /**
     * 最小费用代码
     */
    public String feeCode = null;

    /**
     * 药品类别
     */
    public String drugType = null;

    /**
     * 药品性质
     */
    public String drugQuality = null;

    /**
     * 项目等级
     */
    public DrugItemGradeEnum itemGrade = null;

    /**
     * 规格
     */
    public String specs = null;

    /**
     * 参考零售价
     */
    public Double retailPrice = null;

    /**
     * 参考批发价
     */
    public Double wholesalePrice = null;

    /**
     * 最近购入价
     */
    public Double purchasePrice = null;

    /**
     * 最高零售价
     */
    public Double topRetailPrice = null;

    /**
     * 包装单位
     */
    public String packUnit = null;

    /**
     * 包装单位
     */
    public Integer packQty = null;

    /**
     * 最小单位
     */
    public String minUnit = null;

    /**
     * 剂型编码
     */
    public String doseModelCode = null;

    /**
     * 基本剂型
     */
    public Double baseDose = null;

    /**
     * 剂量单位
     */
    public String doseUnit = null;

    /**
     * 用法编码
     */
    public String usageCode = null;

    /**
     * 频次编码
     */
    public String frequencyCode = null;

    /**
     * 一次用量
     */
    public Double onceDose = null;

    /**
     * 注意事项
     */
    public String caution = null;

    /**
     * 一级药理作用
     */
    public String phyFunction1 = null;

    /**
     * 二级药理作用
     */
    public String phyFunction2 = null;

    /**
     * 三级药理作用
     */
    public String phyFunction3 = null;

    /**
     * 有效性标识
     */
    public DrugValidStateEnum validState = null;

    /**
     * 自产标识
     */
    public Boolean selfFlag = null;

    /**
     * 处方药标识
     */
    public Boolean octFlag = null;

    /**
     * GMP标识
     */
    public Boolean gmpFlag = null;

    /**
     * 皮试标识
     */
    public Boolean testFlag = null;

    /**
     * 新药标识
     */
    public Boolean newFlag = null;

    /**
     * 附材标识
     */
    public Boolean appendFlag = null;

    /**
     * 缺药标识
     */
    public Boolean lackFlag = null;

    /**
     * 大屏显示标志
     */
    public Boolean showFlag = null;

    /**
     * 招标标志
     */
    public Boolean tenderFlag = null;

    /**
     * 招标价
     */
    public Double tenderPrice = null;

    /**
     * 中标公司
     */
    public String tenderCompany = null;

    /**
     * 中标开始时间
     */
    public Date tenderBeginDate = null;

    /**
     * 中标结束时间
     */
    public Date tenderEndDate = null;

    /**
     * 最新供药公司（在入库时更新，用于生成药品采购单）
     */
    public String companyCode = null;

    /**
     * 价格形式
     */
    public String priceForm = null;

    /**
     * 招标采购合同编号
     */
    public String contactCode = null;

    /**
     * 产地
     */
    public String productingArea = null;

    /**
     * 生产厂家
     */
    public String producerCode = null;

    /**
     * 批文信息
     */
    public String approveInfo = null;

    /**
     * 商标
     */
    public String label = null;

    /**
     * 有效成分
     */
    public String ingredient = null;

    /**
     * 执行标准
     */
    public String execStd = null;

    /**
     * 存储条件
     */
    public String storeCond = null;

    /**
     * 药品简介
     */
    public String briefInfo = null;

    /**
     * 说明书内容
     */
    public String manual = null;

    /**
     * 药监局药品编码
     */
    public String barCode = null;

    /**
     * 旧系统药品编码
     */
    public String oldDrugCode = null;

    /**
     * 备注
     */
    public String mark = null;

    /**
     * 操作员
     */
    public String operCode = null;

    /**
     * 操作时间
     */
    public Date operDate = null;

    /**
     * 省限制标识
     */
    public Boolean prFlag = null;

    /**
     * 市限制标识
     */
    public Boolean cityFlag = null;

    /**
     * 自费项目标识
     */
    public Boolean selfPayFlag = null;

    /**
     * 特限药品标识
     */
    public Boolean specialFlag = null;

    /**
     * 住院包药机标识
     */
    public Boolean inpatPackFlag = null;

    /**
     * 变更类型
     */
    public DrugShiftTypeEnum shiftType = null;

    /**
     * 变更时间
     */
    public Date shiftDate = null;

    /**
     * 变更原因
     */
    public String shiftMark = null;

    /**
     * 外观图
     */
    public String tradePic = null;

    /**
     * 是否不可拆装
     */
    public Boolean inseparableFlag = null;

    /**
     * 协定处方标识
     */
    public Boolean noStrumFlag = null;

    /**
     * 扩展字段（库管分类）
     */
    public String extend1 = null;

    /**
     * 扩展信息2
     */
    public String extend2 = null;

    /**
     * 字典创建时间
     */
    public Date createTime = null;

    /**
     * 
     */
    public String drugTypeTwo = null;

    /**
     * 抗菌药物对应编码
     */
    public String actNo = null;

    /**
     * 辅药标识
     */
    public Boolean adjuvantFlag = null;

    /**
     * 抗肿瘤药物标识
     */
    public Boolean antiTumerFlag = null;

    /**
     * 带量采购标识
     */
    public Boolean withQtyFlag = null;

    /**
     * 临购标识
     */
    public Boolean temporaryFlag = null;

    /**
     *国基药标识
     */
    public Boolean baseFlag = null;
    
    /**
     * 国家监测标识
     */
    public Boolean monitorFlag = null;

    /**
     * 集中采购标识
     */
    public Boolean uniPurchaseFlag = null;

    /**
     * 省平台流水号
     */
    public String ppsn = null;

    /**
     * 国家谈判药品
     */
    public Boolean cnegotiateFlag = null;

    /**
     * 院区标识
     */
    public DeptOwnEnum deptOwn = null;

    /**
     * 包装材料
     */
    public String packMaterial = null;
}
