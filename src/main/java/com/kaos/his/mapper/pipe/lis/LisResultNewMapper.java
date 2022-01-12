package com.kaos.his.mapper.pipe.lis;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.pipe.lis.LisResultNew;

public interface LisResultNewMapper {
    /**
     * 查询检验结果
     * 
     * @param patientId
     * @param itemCode
     * @param beginDate
     * @param endDate
     * @return
     */
    List<LisResultNew> queryInspectResult(String patientId, String itemCode, Date beginDate, Date endDate);
}
