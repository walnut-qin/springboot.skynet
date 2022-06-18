package com.kaos.skynet.api.data.his.router;

import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.MetOpsOperationItemMapper;
import com.kaos.skynet.core.type.Router;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SurgeryNameRouter implements Router<String, String> {
    /**
     * 手术项目接口
     */
    @Autowired
    MetOpsOperationItemMapper metOpsOperationItemMapper;

    @Override
    public String route(String operationNo) {
        // 判空
        if (operationNo == null) {
            return null;
        }

        // 执行逻辑
        var item = metOpsOperationItemMapper.queryMetOpsItem(operationNo, "S991");
        if (item != null) {
            return item.getItemName();
        }
        return operationNo;
    }
}