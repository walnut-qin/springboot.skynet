package com.kaos.skynet.api.data.converter;

import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.entity.common.DawnOrgDept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
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
        DawnOrgDept deptInfo = dataCache.getDeptCache().get(deptCode);
        if (deptInfo != null) {
            return deptInfo.getDeptName();
        }
        return deptCode;
    }
}
