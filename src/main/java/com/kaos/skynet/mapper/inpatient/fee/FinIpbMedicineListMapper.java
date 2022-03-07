package com.kaos.skynet.mapper.inpatient.fee;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.entity.inpatient.fee.FinIpbMedicineList;

public interface FinIpbMedicineListMapper {
    /**
     * 查询药品明细
     * 
     * @param deptCode  执行科室, 等于 {@code null} 时，不作为判断条件
     * @param beginDate 执行开始时间, 等于 {@code null} 时，不作为判断条件
     * @param endDate   执行结束时间, 等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinIpbMedicineList> queryMedicineLists(String deptCode, Date beginDate, Date endDate);
}
