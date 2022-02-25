package com.kaos.his.entity.common.drug.hedging;

import java.util.Date;

import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.enums.impl.common.ValidStateEnum;
import com.kaos.his.enums.impl.pharmacy.DrugTypeEnum;

/**
 * 对冲库库存实体
 */
public class PhaComStockInfoHedging {
    /**
     * 药库编码
     */
    public String drugDeptCode = null;

    /**
     * 药品编码
     */
    public String drugCode = null;

    /**
     * 药品性质
     */
    public String drugQuality = null;

    /**
     * 包装单位
     */
    public String packUnit = null;

    /**
     * 包装数量
     */
    public Integer packQty = null;

    /**
     * 最小数量
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
     * 参考零售价
     */
    public Double retailPrice = null;

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
     * 有效期至
     */
    public Date validDate = null;

    /**
     * 货位号
     */
    public String placeCode = null;

    /**
     * 生产厂家
     */
    public String producerCode = null;

    /**
     * 管理性质
     */
    public String manageQuality = null;

    /**
     * 最低库存量
     */
    public Double lowSum = null;

    /**
     * 最高库存量
     */
    public Double topSum = null;

    /**
     * 批采购量
     */
    public Double needBatch = null;

    /**
     * 有效期天数
     */
    public Integer usefulDays = null;

    /**
     * 缺药标识
     */
    public Boolean lackFlag = null;

    /**
     * 日盘点标记
     */
    public Boolean dayChkFlag = null;

    /**
     * 默认发药单位标记（0：最小单位；1：包装单位）
     */
    public String unitFlag = null;

    /**
     * 可拆零标记
     */
    public Boolean changeFlag = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 备注
     */
    public String remark = null;

    /**
     * 操作员编码
     */
    public String emplCode = null;

    /**
     * 操作日期
     */
    public Date operDate = null;

    /**
     * 药品类型
     */
    public DrugTypeEnum drugType = null;

    /**
     * 药柜管理标志
     */
    public Boolean arkFlag = null;

    /**
     * 药柜管理汇总数量
     */
    public Double arkQty = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 实体：药库科室
         */
        public DawnOrgDept drugDept = null;

        /**
         * 实体：操作员
         */
        public DawnOrgEmpl employee = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
