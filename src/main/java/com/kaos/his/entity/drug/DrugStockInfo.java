package com.kaos.his.entity.drug;

import java.sql.Date;

import com.kaos.his.enums.DrugValidStateEnum;

/**
 * 药库主表信息
 */
public class DrugStockInfo {
    /**
     * 科室编码
     */
    public String deptCode = null;

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
     * 最小单位
     */
    public String minUnit = null;

    /**
     * true : 包装单位; false : 最小单位
     */
    public Boolean packUnitFlag = null;

    /**
     * 显示单位
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
     * 预扣库总量
     */
    public Double preOutSum = null;

    /**
     * 预扣总金额
     */
    public Double preOutCost = null;

    /**
     * 有效期
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
     * 最小库存量
     */
    public Double lowSum = null;

    /**
     * 最大库存量
     */
    public Double topSum = null;

    /**
     * 批采购量
     */
    public Double batchSum = null;

    /**
     * 有效天数
     */
    public Integer usefulDays = null;

    /**
     * 缺药标志
     */
    public Boolean lackFlag = null;

    /**
     * 日盘点标志
     */
    public Boolean dayCheckFlag = null;

    /**
     * 发药单位标识， true：发药单位；false：最小单位
     */
    public Boolean unitFlag = null;

    /**
     * 可以拆零标识
     */
    public Boolean changeFlag = null;

    /**
     * 有效性标识
     */
    public DrugValidStateEnum validState = null;

    /**
     * 备注
     */
    public String mark = null;

    /**
     * 操作员
     */
    public String operCode = null;

    /**
     * 操作日期
     */
    public Date operDate = null;

    /**
     * 药品类型
     */
    public String drugType = null;

    /**
     * 药柜管理标识
     */
    public Boolean arkFlag = null;

    /**
     * 药柜管理库存汇总数量
     */
    public Double arkQty = null;
}
