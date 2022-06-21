package com.kaos.skynet.api.data.his.tunnel;

import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.MetOpsOperationItemMapper;
import com.kaos.skynet.core.type.Tunnel;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SurgeryNameTunnel implements Tunnel<String, String> {
    /**
     * 手术项目接口
     */
    @Autowired
    MetOpsOperationItemMapper metOpsOperationItemMapper;

    @Override
    public String tunneling(String operationNo) {
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