package com.kaos.skynet.api.data.converter;

import com.kaos.skynet.api.data.mapper.inpatient.surgery.MetOpsOperationItemMapper;
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
        var item = metOpsOperationItemMapper.queryMetOpsItem(operationNo, "S991");
        if (item != null) {
            return item.getItemName();
        }
        return operationNo;
    }
}