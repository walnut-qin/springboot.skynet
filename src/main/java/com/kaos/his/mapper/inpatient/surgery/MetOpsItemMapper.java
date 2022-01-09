package com.kaos.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.his.entity.inpatient.surgery.MetOpsItem;

public interface MetOpsItemMapper {
    /**
     * 主键查询
     * 
     * @param operationNo
     * @param itemName
     * @return
     */
    MetOpsItem queryMetOpsItem(String operationNo, String itemCode);

    /**
     * 查询某手术所有项目
     * 
     * @param operationNo
     * @return
     */
    List<MetOpsItem> queryMetOpsItems(String operationNo);
}
