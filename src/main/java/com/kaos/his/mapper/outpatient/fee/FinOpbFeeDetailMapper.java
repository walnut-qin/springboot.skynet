package com.kaos.his.mapper.outpatient.fee;

import java.util.Date;

import com.kaos.his.entity.outpatient.fee.FinOpbFeeDetail;

public interface FinOpbFeeDetailMapper {
    /**
     * 查询患者门诊划价
     * 
     * @param cardNo
     * @param itemCode
     * @param beginDate
     * @param endDate
     * @return
     */
    FinOpbFeeDetail queryFeeDetailsWithClinicCode(String clinicCode, String itemCode, Date beginDate, Date endDate);

    /**
     * 查询患者门诊划价
     * 
     * @param cardNo
     * @param itemCode
     * @param beginDate
     * @param endDate
     * @return
     */
    FinOpbFeeDetail queryFeeDetailsWithCardNo(String cardNo, String itemCode, Date beginDate, Date endDate);
}
