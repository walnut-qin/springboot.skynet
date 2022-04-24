package com.kaos.skynet.api.mapper.pipe.lis;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.entity.pipe.lis.LisResultNew;

public interface LisResultNewMapper {
    /**
     * 查询检验结果
     * 
     * @param patientId 住院号或就诊卡号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param itemCodes 检验项目编码；等于 {@code null} 时，不作为判断条件
     * @param beginDate 开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate   结束时间；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<LisResultNew> queryInspectResult(String patientId, List<String> itemCodes, LocalDateTime beginDate,
            LocalDateTime endDate);
}
