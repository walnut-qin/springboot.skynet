package com.kaos.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.his.entity.inpatient.surgery.MetOpsItem;

public interface MetOpsItemMapper {
    /**
     * 主键查询
     * 
     * @param operationNo 手术编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param itemName    项目编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsItem queryMetOpsItem(String operationNo, String itemCode);

    /**
     * 查询某手术所有项目
     * 
     * @param operationNo 手术编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<MetOpsItem> queryMetOpsItems(String operationNo);
}
