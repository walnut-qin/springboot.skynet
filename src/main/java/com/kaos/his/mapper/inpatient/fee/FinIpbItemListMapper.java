package com.kaos.his.mapper.inpatient.fee;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.fee.FinIpbItemList;

public interface FinIpbItemListMapper {
    /**
     * 查询符合条件的记录列表
     * 
     * @param deptCode  执行科室
     * @param beginDate 执行开始时间
     * @param endDate   执行结束时间
     * @return
     */
    List<FinIpbItemList> queryItemLists(String deptCode, Date beginDate, Date endDate);
}
