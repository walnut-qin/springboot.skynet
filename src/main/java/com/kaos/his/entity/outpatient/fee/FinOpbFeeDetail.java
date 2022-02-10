package com.kaos.his.entity.outpatient.fee;

import java.util.Date;

import com.kaos.his.entity.common.Department;
import com.kaos.his.entity.common.Employee;
import com.kaos.his.entity.outpatient.Outpatient;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.enums.common.TransTypeEnum;
import com.kaos.his.enums.outpatient.ClassCodeEnum;
import com.kaos.his.enums.pharmacy.DrugQualityEnum;

/**
 * 门诊费用明细（XYHIS.FIN_OPB_FEEDETAIL）
 */
public class FinOpbFeeDetail {
    /**
     * 处方单号
     */
    public String recipeNo = null;

    /**
     * 处方内项目流水号
     */
    public Integer seqNo = null;

    /**
     * 业务类型
     */
    public TransTypeEnum transType = null;

    /**
     * 门诊号
     */
    public String clinicCode = null;

    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 挂号日期
     */
    public Date regDate = null;

    /**
     * 挂号科室
     */
    public String regDeptCode = null;

    /**
     * 开方医师
     */
    public String docCode = null;

    /**
     * 开方医师所在科室
     */
    public String docDeptCode = null;

    /**
     * 项目编码
     */
    public String itemCode = null;

    /**
     * 项目名称
     */
    public String itemName = null;

    /**
     * 是否为药品
     */
    public Boolean drugFlag = null;

    /**
     * 规格
     */
    public String specs = null;

    /**
     * 自制药标识
     */
    public Boolean selfMade = null;

    /**
     * 药品性质
     */
    public DrugQualityEnum drugQuality = null;

    /**
     * 剂型
     */
    public String doseModelCode = null;

    /**
     * 最小费用编码
     */
    public MinFeeEnum feeCode = null;

    /**
     * 系统类别
     */
    public ClassCodeEnum classCode = null;

    /**
     * 单价
     */
    public Double unitPrice = null;

    /**
     * 数量
     */
    public Double qty = null;

    /**
     * 草药的付数，其他药品为1
     */
    public Integer days = null;

    /**
     * 频次代码
     */
    public String frequencyCode = null;

    /**
     * 用法代码
     */
    public String usageCode = null;

    /**
     * 用法名称
     */
    public String useName = null;

    /**
     * 院内注射次数
     */
    public Integer injectNumber = null;

    /**
     * 加急标志
     */
    public Boolean emergencyFlag = null;

    /**
     * 样本类型
     */
    public String labType = null;

    /**
     * 检体
     */
    public String checkBody = null;

    /**
     * 每次用量
     */
    public Double doseOnce = null;

    /**
     * 每次用量单位
     */
    public String doseUnit = null;

    /**
     * 基本剂量
     */
    public Double baseDose = null;

    /**
     * 包装数量
     */
    public Integer packQty = null;

    /**
     * 计价单位
     */
    public String priceUnit = null;

    /**
     * 统筹金额
     */
    public Double pubCost = null;

    /**
     * 自付金额
     */
    public Double payCost = null;

    /**
     * 现金金额
     */
    public Double ownCost = null;

    /**
     * 执行科室编码
     */
    public String execDeptCode = null;

    /**
     * 划价人编码
     */
    public String feeEmplCode = null;

    /**
     * 划价时间
     */
    public Date feeDate = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：门诊患者
         */
        public Outpatient outpatient = null;

        /**
         * 开单科室
         */
        public Department regDept = null;

        /**
         * 开方医师
         */
        public Employee doctor = null;

        /**
         * 实体：执行科室
         */
        public Department execDept = null;

        /**
         * 实体：划价员
         */
        public Employee feeEmpl = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
