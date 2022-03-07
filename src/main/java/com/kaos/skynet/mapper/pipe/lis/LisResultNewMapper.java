package com.kaos.skynet.mapper.pipe.lis;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.entity.pipe.lis.LisResultNew;

public interface LisResultNewMapper {
    /**
     * 查询检验结果
     * 
     * @param patientId 住院号或就诊卡号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param itemCode  住院号或就诊卡号；等于 {@code null} 时，不作为判断条件
     * @param beginDate 住院号或就诊卡号；等于 {@code null} 时，不作为判断条件
     * @param endDate   住院号或就诊卡号；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<LisResultNew> queryInspectResult(String patientId, String itemCode, Date beginDate, Date endDate);
}
