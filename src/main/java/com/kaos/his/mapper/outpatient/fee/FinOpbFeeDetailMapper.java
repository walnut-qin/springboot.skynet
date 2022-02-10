package com.kaos.his.mapper.outpatient.fee;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.outpatient.fee.FinOpbFeeDetail;

public interface FinOpbFeeDetailMapper {
    /**
     * 查询患者门诊划价
     * 
     * @param clinicCode 门诊号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param itemCode   项目编码；等于 {@code null} 时，不作为判断条件
     * @param beginDate  开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate    结束时间；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinOpbFeeDetail> queryFeeDetailsWithClinicCode(String clinicCode, String itemCode, Date beginDate,
            Date endDate);

    /**
     * 查询患者门诊划价
     * 
     * @param cardNo    就诊卡号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param itemCode  项目编码；等于 {@code null} 时，不作为判断条件
     * @param beginDate 开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate   结束时间；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinOpbFeeDetail> queryFeeDetailsWithCardNo(String cardNo, String itemCode, Date beginDate, Date endDate);

    /**
     * 插入费用明细
     * 
     * @param feeDetail 待插入实体
     * @return
     */
    int insertFeeDetail(FinOpbFeeDetail feeDetail);
}
