package com.kaos.skynet.api.entity.common.drug.hedging;

import java.util.Date;

import com.kaos.skynet.api.entity.common.DawnOrgDept;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.pharmacy.DrugQualityEnum;
import com.kaos.skynet.api.enums.pharmacy.DrugTypeEnum;

/**
 * 对冲明细表
 */
public class PhaComStorageHedging {
    /**
     * 批次号
     */
    public String groupCode = null;

    /**
     * 药品库存科室
     */
    public String drugDeptCode = null;

    /**
     * 药品编码
     */
    public String drugCode = null;

    /**
     * 批号
     */
    public String batchNo = null;

    /**
     * 药品商品名
     */
    public String tradeName = null;

    /**
     * 规格
     */
    public String spec = null;

    /**
     * 药品类型
     */
    public DrugTypeEnum drugType = null;

    /**
     * 药品性质
     */
    public DrugQualityEnum drugQuality = null;

    /**
     * 零售价
     */
    public Double retailPrice = null;

    /**
     * 批发价
     */
    public Double wholesalePrice = null;

    /**
     * 实进价
     */
    public Double purchasePrice = null;

    /**
     * 包装单位
     */
    public String packUnit = null;

    /**
     * 包装数量
     */
    public Integer packQty = null;

    /**
     * 最小单位
     */
    public String minUnit = null;

    /**
     * 显示单位标记（0：最小单位；1：包装单位）
     */
    public String showFlag = null;

    /**
     * 显示的单位
     */
    public String showUnit = null;

    /**
     * 有效期至
     */
    public Date validDate = null;

    /**
     * 总数量
     */
    public Double storeSum = null;

    /**
     * 总金额
     */
    public Double storeCost = null;

    /**
     * 预扣库存数量
     */
    public Double preOutSum = null;

    /**
     * 预扣总金额
     */
    public Double preOutCost = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 生产厂家
     */
    public String producerCode = null;

    /**
     * 最近一次月结库存
     */
    public Double lastMonthNum = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：药库科室
         */
        public DawnOrgDept drugDept = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
