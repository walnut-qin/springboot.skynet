package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.MetOpsOperationItem;

public interface MetOpsOperationItemMapper {
    /**
     * 主键查询
     * 
     * @param operationNo 手术编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param itemName    项目编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsOperationItem queryMetOpsItem(String operationNo, String itemCode);

    /**
     * 查询某手术所有项目
     * 
     * @param operationNo 手术编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<MetOpsOperationItem> queryMetOpsItems(String operationNo);
}
