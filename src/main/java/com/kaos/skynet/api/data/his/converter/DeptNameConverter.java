package com.kaos.skynet.api.data.his.converter;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.api.data.his.entity.common.DawnOrgDept;
import com.kaos.skynet.core.type.converter.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 科室编码到科室名称的转换器
 */
@Component
public class DeptNameConverter implements Converter<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String convert(String deptCode) {
        // 判空
        if (deptCode == null) {
            return null;
        }

        // 执行逻辑
        DawnOrgDept deptInfo = dataCache.getDeptCache().get(deptCode);
        if (deptInfo != null) {
            return deptInfo.getDeptName();
        }
        return deptCode;
    }
}
