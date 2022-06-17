package com.kaos.skynet.api.data.his.converter;

import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.MetOpsOperationItemMapper;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SurgeryNameConverter implements Converter<String, String> {
    /**
     * 手术项目接口
     */
    @Autowired
    MetOpsOperationItemMapper metOpsOperationItemMapper;

    @Override
    public String convert(String operationNo) {
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